import repositories.PersonRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        PersonRepository repository = context.getBean(PersonRepository.class);
        repository.findAll().forEach(x->System.out.println(x.getName()));
    }
}
