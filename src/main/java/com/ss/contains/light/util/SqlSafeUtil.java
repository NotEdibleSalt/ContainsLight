package com.ss.contains.light.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 更严格的SQL注入检测
 */
public class SqlSafeUtil {
    /**
     * SQL语法检查正则：符合两个关键字（有先后顺序）才算匹配
     * <p>
     * 参考: mybatis-plus-core/src/main/java/com/baomidou/mybatisplus/core/toolkit/sql/SqlInjectionUtils.java
     */
    private static final Pattern SQL_SYNTAX_PATTERN = Pattern.compile("(insert|delete|update|select|create|drop|truncate|grant|alter|deny|revoke|call|execute|exec|declare|show|rename|set)" +
                                                                      ".+(into|from|set|where|table|database|view|index|on|cursor|procedure|trigger|for|password|union|and|or)", Pattern.CASE_INSENSITIVE);
    /**
     * 使用'、;或注释截断SQL检查正则
     * <p>
     * 参考: mybatis-plus-core/src/main/java/com/baomidou/mybatisplus/core/toolkit/sql/SqlInjectionUtils.java
     */
    private static final Pattern SQL_COMMENT_PATTERN = Pattern.compile("'.*(or|union|--|#|/*|;)", Pattern.CASE_INSENSITIVE);

    /**
     * 检查参数是否存在 SQL 注入
     *
     * @param value 检查参数
     */
    public static void check(String value) {
        // 不允许使用任何函数（不能出现括号），否则无法检测后面这个注入 order by id,if(1=2,1,(sleep(100)));
        boolean b = value.contains("(") || SQL_COMMENT_PATTERN.matcher(value).find() || SQL_SYNTAX_PATTERN.matcher(value).find();
        if (b){
            throw new RuntimeException("参数存在注入风险");
        }
    }

    static String checkWhere(String where) {

        if (!where.isEmpty()) {
            if (where.toUpperCase().matches("^(WHERE|ORDER +BY|GROUP +BY|LIMIT|OFFSET) +.*")){
                throw new IllegalArgumentException("WHERE expression must start with 'WHERE|ORDER BY|GROUP BY|LIMIT|OFFSET'(case insensitive)");
            }

            /** 禁止字符串常量比较
             *  如 'owner_id'='owner' "_id"
             *  禁止左右完全相等的比较
             *  如 hello=hello
             *  禁止数字常量比较
             *  如 7.0=7
             *  如  .12='12'
             *  如 ".1"=.1
             * */
            checkMatchFind("((\'[^\']*\'\\s*|\"[^\"]*\\\"\\s*)+\\s*=\\s*(\'[^\']*\'\\s*|\"[^\"]*\"\\s*)+|([+-]?(?:\\d*\\.)?\\d+)\\s*=\\s*[+-]?(?:\\d*\\.)?\\d+|([^\'\"\\s]+)\\s*=\\s*\\5\\b|([+-]?(?:\\d*\\.)?\\d+)\\s*=\\s*(\'|\")[+-]?(?:\\d*\\.)?\\d+\\s*\\7|(\'|\")([+-]?(?:\\d*\\.)?\\d+)\\s*\\8\\s*=\\s*[+-]?(?:\\d*\\.)?\\d+)", 0, where, "INVALID WHERE equation  expression");

            /**
             * 禁止恒为true的判断条件
             * -- 禁止 非0数字常量为判断条件
             * -- 禁止 not false,not true
             * 如: where "-055.55asdfsdfds0"  or  true  or not false
             */
            Matcher m1 = regexMatcher("((?:where|or)\\s+)(not\\s+)?(false|true|(\'|\")([+-]?\\d+(\\.\\d+)?).*\\4)", Pattern.CASE_INSENSITIVE, where);
            while (m1.find()) {
                boolean not = null != m1.group(2);
                String g3 = m1.group(3);
                Boolean isTrue;
                if (g3.equalsIgnoreCase("true")) {
                    isTrue = true;
                } else if (g3.equalsIgnoreCase("false")) {
                    isTrue = false;
                } else {
                    String g5 = m1.group(5);
                    isTrue = 0 != Double.valueOf(g5);
                }
                if (not) {
                    isTrue = !isTrue;
                }

                if (!isTrue){
                    throw new IllegalArgumentException("INVALID WHERE const true  expression " + m1.group());
                }

            }
            /**
             * 禁止字符串常量或数字常量开头的 IN语句
             * 如 7.0 IN ( 15, 7)
             * 如 'hello' IN ( 'hello', 'world')
             * */
            checkMatchFind("(((\'|\")[^\']*\\3\\s*)|[\\d\\.+-]+\\s*)\\s+IN\\s+\\(.*\\)", Pattern.CASE_INSENSITIVE, where, "INVALID IN expression");
            /** 删除where中所有字符串常量,再进行关键字检查,避免字符串中的包含的关键引起误判 */
            String nonestr = where.replaceAll("(\'[^\']*\'|\"[^\"]*\")", "");
            checkMatchFind("\\b(exec|insert|delete|update|join|union|master|truncate)\\b", Pattern.CASE_INSENSITIVE, nonestr, "ILLEGAL SQL key");
        }
        return where;
    }

    private static void checkMatchFind(String regex, int flags, String input, String errmsg) {

        Matcher matcher = regexMatcher(regex, flags, input);
        if (matcher.find()) {
            throw new IllegalArgumentException(String.format(errmsg + " '%s'", matcher.group()));
        }
    }

    private static Matcher regexMatcher(String regex, int flags, String input) {
        return Pattern.compile(regex, flags).matcher(input);
    }
}