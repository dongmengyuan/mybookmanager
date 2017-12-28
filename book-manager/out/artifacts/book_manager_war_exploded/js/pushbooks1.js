function pushselect() {
    var two = [
        ['汇编', 'C', 'C++', 'JAVA', 'Python', 'PHP', 'C#', 'JavaScript', 'R', 'MATLAB', 'Go'],
        ['竞赛', '基础数据结构与算法', '进阶'],
        ['设计模式', 'UML', '软件测试', '软件开发'],
        ['MySQL', 'Redis', '关系型数据库', 'NoSQL', 'MongoDb', 'Oracle', '数据库设计与实现原理'],
        ['Linux', 'Unix', 'Windox', '系统开发', '操作系统理论'],
        ['网络管理', '网络组建', '网络协议', '网络理论', 'WebServer'],
        ['JAVA-EE', 'Spring', 'Hibernate', 'Struts', '后台开发'],
        ['HTML', 'CSS', 'JavaScript', 'Jquery', 'Json', 'Ajax', 'node.js', 'ES', 'Vue', 'React'],
        ['机器学习', '深度学习', '神经网络', 'AR、VR'],
        ['Hadop', 'Sqark', '分布式存储', '数据挖掘', '数据分析'],
        ['内核剖析', '编译原理', '设备与驱动', '嵌入式', 'Vim'],
        ['密码学', '黑客文化', '信息安全', '防火墙'],
        ['高数']
    ]
    var select = document.getElementById("select");
    var input = document.getElementById("select_input");
    var con = document.getElementById("select_con");
    var ul = document.getElementById("ul");
    var one = document.getElementById("one");
    var onelis = one.getElementsByTagName("li");

    input.onfocus = function() {

        con.style.display = "block";
        onelis[0].style.color = "white";
        onelis[0].style.backgroundColor = "#009A61";
        for (var i = 0; i < two[0].length; i++) {
            var li = document.createElement("li");
            var text = document.createTextNode(two[0][i]);
            ul.appendChild(li);
            li.appendChild(text);
        }
        var lis = ul.getElementsByTagName("li");
        for (var m = 0, len1 = lis.length; m < len1; m++) {
            lis[m].onclick = function() {

                var litext = this.innerHTML;
                input.value += litext + '、';
            }
        }
        for (var i = 0, len = onelis.length; i < len; i++) {
            (function() {
                var n = i;
                onelis[i].onclick = function() {
                    for (var j = 0; j < len; j++) {
                        onelis[j].style.color = "#666";
                        onelis[j].style.backgroundColor = "white";
                    }
                    this.style.color = "white";
                    this.style.backgroundColor = "#009A61";
                    ul.innerHTML = "";
                    for (var k = 0, len0 = two[n].length; k < len0; k++) {
                        var li = document.createElement("li");
                        var text = document.createTextNode(two[n][k]);
                        ul.appendChild(li);
                        li.appendChild(text);
                    }
                    var lis = ul.getElementsByTagName("li");
                    for (var m = 0, len1 = lis.length; m < len1; m++) {
                        lis[m].onclick = function() {

                            var litext = this.innerHTML;
                            input.value += litext + '、';
                        }
                    }
                }
            })();
        }
        con.focus();
    }

    con.onblur = function() {
        ul.innerHTML = "";
        for (var j = 0; j < onelis.length; j++) {
            onelis[j].style.color = "#666";
            onelis[j].style.backgroundColor = "white";
        }
        con.style.display = "none";
    }
}
addLoadEvent(pushselect);