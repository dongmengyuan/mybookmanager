package bookmanager.model.po;

/**
 * Created by dela on 12/27/17.
 */
public class PagePO {
    private static int everyPage;      // 每页显示记录数
    private int totalCount;     // 总记录数
    private int totalPage;      // 总页数
    private int currentPage;    // 当前页
    private int beginIndex;     // 查询起始点
    private boolean hasPrePage; // 是否有上一页
    private boolean hasNexPage; // 是否有下一页

    static {
        everyPage = 5;
    }

    public PagePO() { }

    public PagePO(int currentPage) {
        this.currentPage = currentPage;
        this.beginIndex = (currentPage - 1) * everyPage;
    }

    public PagePO(int everyPage, int currentPage, int beginIndex,
                  boolean hasPrePage, boolean hasNexPage) {
        this.everyPage = everyPage;
        this.currentPage = currentPage;
        this.hasPrePage = hasPrePage;
        this.hasNexPage = hasNexPage;
        this.beginIndex = beginIndex;
    }

    public PagePO(int everyPage, int totalCount, int totalPage, int currentPage,
                  int beginIndex, boolean hasPrePage, boolean hasNexPage) {
        this.everyPage = everyPage;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.beginIndex = beginIndex;
        this.hasPrePage = hasPrePage;
        this.hasNexPage = hasNexPage;
    }

    public int getEveryPage() {
        return everyPage;
    }

    public void setEveryPage(int everyPage) {
        this.everyPage = everyPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNexPage() {
        return hasNexPage;
    }

    public void setHasNexPage(boolean hasNexPage) {
        this.hasNexPage = hasNexPage;
    }
}
