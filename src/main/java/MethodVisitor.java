import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);
        if(!n.getAnnotationByName("Test").isPresent()) {
            return;
        }

//        var exception = n.getAnnotationByName("Test").map(a -> {
//
//        });

    }
}
