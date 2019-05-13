import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TestAnnotationVisitor extends VoidVisitorAdapter<String> {

    @Override
    public void visit(SingleMemberAnnotationExpr n, String arg) {
        super.visit(n, arg);

//        if(n.getNameAsString().equals("Test"))
    }
}
