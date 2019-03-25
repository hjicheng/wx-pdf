package cn.hjc.pdf.util;

/**
 * @Author: hjc
 * @Date: 2019/3/25 21:44
 * @Version 1.0
 */

public enum PdfUserEnum {

    LIKE("点赞",1),LOOK("浏览",2),DOWN("下载",3);

    private PdfUserEnum(String type,Integer num){
        this.num = num;
        this.type = type;
    }

    public static Integer getNum(String type){
        for (PdfUserEnum pdfUserEnum : PdfUserEnum.values()){
            if (pdfUserEnum.getType().equals(type)) {
                return pdfUserEnum.num;
            }
        }
        return null;
    }


    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    private Integer num;



}
