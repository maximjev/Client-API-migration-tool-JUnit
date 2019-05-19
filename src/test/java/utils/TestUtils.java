package utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;

import java.util.Map;

import static java.lang.String.format;

public class TestUtils {

    private static String CLASS_TEMPLATE = "public class Foo { %s } \n ";
    private static String METHOD_TEMPLATE = "public void bar() { %s } \n ";
    private static String ANNOTATION_TEMPLATE = "@%s \n ";
    private static String ANNOTATION_PARAM_TEMPLATE = "@%s(%s = %s) \n ";
    private static String IMPORT_PARAM_TEMPLATE = "import %s%s; \n ";
    private static String STATIC_IMPORT_TEMPLATE = "import static %s%s.*; \n ";

    public static String prettyPrint(String code) {
        return StaticJavaParser.parse(code).toString();
    }

    public static String classWrap(String code) {
        return format(CLASS_TEMPLATE, code);
    }

    public static String methodWrap(String code) {
        return format(METHOD_TEMPLATE, code);
    }

    public static String appendBeforeEmptyClass(String code) {
        return code + format(CLASS_TEMPLATE, "");
    }

    public static String appendBeforeEmptyMethod(String code) {
        return code + format(METHOD_TEMPLATE, "");
    }

    public static String appendStaticImport(String code, String importString, String param) {
        return format(STATIC_IMPORT_TEMPLATE, importString, param) + code;
    }

    public static String appendImport(String code, String importString, String param) {
        return format(IMPORT_PARAM_TEMPLATE, importString, param) + code;
    }

    public static String appendAnnotation(String code, String annotation) {
        return format(ANNOTATION_TEMPLATE, annotation) + code;
    }

    public static String appendAnnotation(String code, String annotation, String param, String value) {
        return format(ANNOTATION_PARAM_TEMPLATE, annotation, param, value) + code;
    }

    public static String appendAnnotations(String code, String annotation, Map<String, String> params) {
        StringBuilder builder = new StringBuilder().append("@" + annotation + "(");
        params.forEach((key, value) -> builder.append(" " + key + "=" + value + ", "));
        builder.delete(builder.length() - 2, builder.length()).append(") \n");
        builder.append(code);
        return builder.toString();
    }

    public static String constructClassForMethodAnnotation(String annotation, String annotationImport) {
        return appendImport(
                classWrap(appendBeforeEmptyMethod(format(ANNOTATION_TEMPLATE, annotation))),
                annotationImport,
                annotation
        );
    }
}
