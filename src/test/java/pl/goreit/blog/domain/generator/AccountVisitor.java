package pl.goreit.blog.domain.generator;

public interface AccountVisitor {
    boolean visit(GraduateAccount account);
}
