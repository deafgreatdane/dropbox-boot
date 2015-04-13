import com.workrefined.dropbox.DropboxConfigForm;

@Component
@Grab(group='com.workrefined', module='dropbox-boot', version='0.1.0')
public class MainController implements CommandLineRunner {
 
	@Autowired DropboxConfigForm form
	
	public void run(String... args) throws Exception {
		form.prompt()
	}

}
