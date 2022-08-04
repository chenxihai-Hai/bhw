import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @org.junit.Test
    public void tets(){
        System.out.println(encoder.encode("rx058177sasasasasa"));
    }
}
