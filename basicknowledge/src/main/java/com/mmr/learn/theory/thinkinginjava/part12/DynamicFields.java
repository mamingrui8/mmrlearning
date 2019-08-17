package com.mmr.learn.theory.thinkinginjava.part12;

/**
 * 属性动态扩展的类
 */
public class DynamicFields {
    //容器，第一维存储属性的序号，第二维存储属性的名称和值
    private Object[][] fields;

    DynamicFields(int initialSize){
        fields = new Object[initialSize][2];
        for(int i = 0; i < initialSize; i++){
            fields[i]  = new Object[]{null, null};
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        //打印当前对象的所有属性名称和属性值
        for(Object[] obj : fields){
            result.append(obj[0]);
            result.append(": ");
            result.append(obj[1]);
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * 查看当前对象是否包含该属性
     * @param id 待查询的属性名称
     * @return int 属性在对象中属性列表的位置
     */
    private int hasField(String id){
        for(int i = 0; i < fields.length; i++)
            if(id.equals(fields[i][0]))
                return i;
        return -1;
    }

    private int getFieldNumber(String id) throws NoSuchFieldException {
        int fieldNum = hasField(id);
        if(fieldNum == -1)
            throw new NoSuchFieldException();
        return fieldNum;
    }

    /**
     * 为对象新增一条属性
     * @param id 待新增的属性名称
     * @return int 新增的属性在对象属性列表中的位置
     */
    private int makeField(String id) {
        //若已有属性列表中存在属性名称为null的情况，则新增属性名至此  (新增值)
        for(int i = 0; i < fields.length; i++)
            if(fields[i][0] == null){
                fields[i][0] = id;
                return i;
            }
        //新增一条属性  (新增位置)
        Object[][] tmp = new Object[fields.length + 1][2];
        for(int i = 0; i < fields.length; i++) //克隆现存的属性
            tmp[i] = fields[i];
        for(int i = fields.length; i < tmp.length; i++)
            tmp[i] = new Object[]{null, null};
        fields =  tmp;
        return makeField(id);
    }

    /**
     * 获取指定属性的值
     * @param id 待查询的属性名称
     * @return 属性值
     * @throws NoSuchFieldException 当没有这个属性时，会报错
     */
    public Object getField(String id) throws NoSuchFieldException{
        return fields[getFieldNumber(id)][1];
    }

    public Object setField(String id, Object value) throws DynamicFieldsException{
        if(value == null){
            DynamicFieldsException dfe = new DynamicFieldsException();
            dfe.initCause(new NullPointerException());
            throw dfe;
        }
        int fieldNumber = hasField(id);
        if(fieldNumber == -1)
            fieldNumber = makeField(id);
        Object result = null;
        try{
            result = getField(id);
        } catch (NoSuchFieldException e){ //这种根据名称找不到对应的属性去赋值的情况只能在运行时发现，因此抛出RuntimeException异常
           throw new RuntimeException();
        }
        fields[fieldNumber][1] = value;
        return result;
    }

    public static void main(String[] args) {
        DynamicFields df = new DynamicFields(3);
        System.out.println(df);

        try{
            df.setField("d", "A value for d");
            df.setField("number", 47);
            df.setField("number2", 48);
            System.out.println(df);
            df.setField("d", "A new value for d");
            df.setField("number3", 11);

            System.out.println("df: " + df);
            System.out.println("df.getField(\"d\") : " + df.getField("d"));

            Object field = df.setField("d" ,null);
        }catch (NoSuchFieldException e) {
            e.printStackTrace(System.out);
        }catch (DynamicFieldsException e) {
            e.printStackTrace(System.out);
        }
    }
}

class DynamicFieldsException extends Exception{ }

