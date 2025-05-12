
import br.com.fiap.profile.KafkaTestResourceLifecycleManager;
import br.com.fiap.profile.MongoProfile;
import io.quarkiverse.cucumber.CucumberQuarkusTest;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.TestProfile;

@TestProfile(MongoProfile.class)
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
class RunCucumberTest extends CucumberQuarkusTest {
    public static void main(String[] args) {
        runMain(RunCucumberTest.class, args);
    }
}
