package bookmanager.dao.rowmapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.*;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by dela on 12/5/17.
 */

// 在这里定义一个能在JdbcTemplate中通用的泛型JdbcRowMapper类
public class JdbcRowMapper<T> implements RowMapper {

    // 记录日志
    protected final Log log = LogFactory.getLog(getClass());

    // 需要映射到的具体类
    private Class<T> mappedClass;

    // 检查Filed是否完全填充
    private boolean checkFullPopulated = false;

    // 原始值默认为null
    private boolean primitivesDefaultedForNullValue = false;

    // 一般来说, filed是数据库表中的字段, property是POJO的属性
    // 但是如果需要通过反射取得class中的成员属性的时候也使用Filed
    private Map<String, PropertyDescriptor> mappedFileds;
    private Set<String> mappedProperties;


    public JdbcRowMapper() { }

    public JdbcRowMapper(Class<T> mappedClass) {
        initialize(mappedClass);
    }

    public JdbcRowMapper(Class<T> mappedClass, boolean checkFullPopulated) {
        initialize(mappedClass);
        this.checkFullPopulated = checkFullPopulated;
    }

    // 为JdbcRowMapper注入mappedClass成员, 以便确定泛型的类型
    public void setMappedClass(Class<T> mappedClass) {
        // 如果mappedClass为空, 那就执行初始化操作
        if (null == this.mappedClass) {
            initialize(mappedClass);
        } else {
            // 如果mappedClass不为空, 则抛出异常:不能给已经映射的mappedClass重新分配mappedClass
            // 因为泛型的类型一旦确定, 就不能再发生改变.
            if (this.mappedClass != null) {
                throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " +
                        mappedClass + " since it is already providing mapping for " + this.mappedClass);
            }
        }
    }

    public final Class<T> getMappedClass() {
        return this.mappedClass;
    }

    public void setCheckFullPopulated(boolean checkFullPopulated) {
        this.checkFullPopulated = checkFullPopulated;
    }

    public boolean isCheckFullPopulated() {
        return this.checkFullPopulated;
    }

    public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
        this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
    }

    public boolean isPrimitivesDefaultedForNullValue() {
        return this.primitivesDefaultedForNullValue;
    }

    // 初始化给定类的映射
    protected void initialize(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.mappedFileds = new HashMap<String, PropertyDescriptor>();
        this.mappedProperties = new HashSet<String>();

        // 先使用反射拿到mappedClass中的所有属性成员(property)
        PropertyDescriptor[] propertyDescriptors
                = BeanUtils.getPropertyDescriptors(mappedClass);

        // propertyDescriptor.getWriteMethod(): 获得用于写入属性值的方法.
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getWriteMethod() != null) {
                String name = propertyDescriptor.getName();

                // Column: 数据库字段与类属性对应关系
                // 在每一个与数据库的表对应的POJO的成员属性上都有一个@Column注解
                try {
                    // 通过反射取得class里名为name的属性
                    // Class.getFiled(): 获得Class中的public属性成员
                    // Class.getDeclaredFiled(): 获得Class中的所有属性成员(包含private/protected)
                    Field field = mappedClass.getDeclaredField(name);

                    // 如果该成员属性存在, 并且带有@Column注解, @Column注解里面的name变量中存放的就是该成员属性对应的数据库中的字段的名字,
                    // 那么就要优先将注解里面设置的默认值设置到Class的该成员属性上去
                    if (field != null) {
                        // 通过field.getAnnotation()方法取得名为@Column的注解
                        Column column = field.getAnnotation(Column.class);

                        // 如果确认该@Column注解存在, 则将取出name变量的值(即数据库字段的名字)
                        // 比如Class中的成员属性的名字为userName, 那么数据库表中的字段的名字就为user_name
                        if (column != null) {
                            name = column.name();
                        }
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                // 将<属性名字, 属性>对加入map中
                this.mappedFileds.put(lowerCaseName(name), propertyDescriptor);
                String underscoredName = underscoreName(name);
                if (!lowerCaseName(name).equals(underscoredName)) {
                    this.mappedFileds.put(underscoredName, propertyDescriptor);
                }
                this.mappedProperties.add(name);
            }
        }
    }

    // lowerCaseName和underscoreName这两个方法的作用是将Java POJO中的属性名转换为对应的数据库中表的字段的名字
    // 比如将userName转换为user_name
    protected String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }

    protected String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(lowerCaseName(name.substring(0, 1)));
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = lowerCaseName(s);
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            }
            else {
                result.append(s);
            }
        }
        return result.toString();
    }

    public T mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        // Spring的断言表达式, 如果前面的布尔表达式为false, 那么后面的message将会以异常消息的形式抛出.
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        // 将mappedClass实例化为一个object
        T mappedObject = BeanUtils.instantiate(this.mappedClass);


        // 使用BeanWrapper将object包装成Bean
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        initBeanWrapper(beanWrapper);

        // 从resultSet中拿到数据库执行之后的数据进行数据填充
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        // resultSet中存放着查询结果的个数
        int columnCount = resultSetMetaData.getColumnCount();
        // isCheckFullPopulated(): 判断是否已经将属性填充完毕
        Set<String> populatedProperties = (isCheckFullPopulated() ? new HashSet<String>() : null);

//        System.out.println(columnCount);

        // 按照index逐一取出数据名称及数据, 再填充到前面实例化之后的object中
        for (int index = 1; index <= columnCount; index++) {
            // 得到该index对应的字段名字
            String column = JdbcUtils.lookupColumnName(resultSetMetaData, index);
            String field = lowerCaseName(column.replaceAll(" ", ""));

            // 根据上面得到的字段名字, 得到POJO中对应的成员属性
            PropertyDescriptor propertyDescriptor = this.mappedFileds.get(field);
            if (propertyDescriptor != null) {
                try {
                    // 得到该field所对应的数据库表中字段(column)所对应的值(value)
                    Object value = getColumnValue(resultSet, index, propertyDescriptor);
                    // 错误日志处理
                    if (rowNumber == 0 && log.isDebugEnabled()) {
                        log.debug("Mapping column '" + column + "' to property '" + propertyDescriptor.getName()
                                + "' of type [" + ClassUtils.getQualifiedName(propertyDescriptor.getPropertyType()) + "]");
                    }

                    try {
                        // 使用BeanWrapper(Bean打包器)将从数据库中查询到的值设置给相应成员属性
                        beanWrapper.setPropertyValue(propertyDescriptor.getName(), value);

                        // 可能会抛出类型不匹配的异常
                    } catch (TypeMismatchException ex) {
                        // 对异常分情况进行处理: 当value为null的时候
                        if (value == null && this.primitivesDefaultedForNullValue) {
                            if (log.isDebugEnabled()) {
                                log.debug("Intercepted TypeMismatchException for row " + rowNumber +
                                        " and column '" + column + "' with null value when setting property '" +
                                        propertyDescriptor.getName() + "' of type [" +
                                        ClassUtils.getQualifiedName(propertyDescriptor.getPropertyType()) +
                                        "] on object: " + mappedObject, ex);
                            }
                        } else {
                            throw ex;
                        }
                    }
                    if (populatedProperties != null) {
                        populatedProperties.add(propertyDescriptor.getName());
                    }
                } catch (NotWritablePropertyException ex) {
                    throw new DataRetrievalFailureException(
                            "Unable to map column '" + column + "' to property '" + propertyDescriptor.getName() + "'", ex);
                }
            } else {
                // 没有发现PropertyDescriptor
                if (rowNumber == 0 && log.isDebugEnabled()) {
                    log.debug("No property found for column '" + column + "' mapped to field '" + field + "'");
                }
            }
        }

        if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
            throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all fields " +
                    "necessary to populate object of class [" + this.mappedClass.getName() + "]: " +
                    this.mappedProperties);
        }

        return mappedObject;
    }

    protected void initBeanWrapper(BeanWrapper beanWrapper) {
    }

    // 得到数据库表中字段(column)对应的值(value)
    protected Object getColumnValue(ResultSet resultSet, int index,
                                    PropertyDescriptor propertyDescriptor) throws SQLException {
        return JdbcUtils.getResultSetValue(resultSet, index, propertyDescriptor.getPropertyType());
    }

    // 工厂模式中, 经常使用newInstance()来创建一个对象
    public static <T> JdbcRowMapper<T> newInstance (Class<T> mappedClass) {
        return new JdbcRowMapper<T>(mappedClass);
    }
}
