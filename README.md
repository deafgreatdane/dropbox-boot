# README

This module provides a simple factory for the Dropbox core API client using spring-boot conventions. The idea is to 
save the developer from having to a find their own conventions for storing the access token.

It allows a single DbxClient per running app, so this isn't for you if you want multiple users to connect to their
dropbox account.


# Installation

Add the dependency to your pom.xml

    <dependency>
		<groupId>com.workrefined</groupId>
		<artifactId>dropbox-boot</artifactId>
		<version>0.1.0</version>
	</dependency>

Add the following properties to your application.properties

    com.workrefined.dropbox.clientIdentifier=Your app name
    com.workrefined.dropbox.appKey=XXXXXXXXXX
    com.workrefined.dropbox.appSecret=YYYYYYYYYYY
    com.workrefined.dropbox.accessToken=ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ

Now your class is really easy

    public class FooService {
    	@Autowired DropboxClientFactory dropboxClientFactory;
    	
    	public void someMethod() throws DbxException {
    		DbxClient dropbox = dropboxClientFactory.getClient();
			DbxEntry.WithChildren children = dropbox.getMetadataWithChildren("/");	
    		...
    	}
    }

# Getting your access token

One of the tricks of using the Core API is getting your access token. Since this use case
deals with a single Dropbox user per app instance, it's doubtful you want a whole interactive 
UI to manage (and protect) for going through the whole Oauth granting flow.

The Dropbox Core API [getting started docs](https://www.dropbox.com/developers/core/start/java)
show how to get an access token, but how would you put that in a real app?

This leverages the [Spring Boot CLI](http://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-installing-the-cli),
make sure you have that installed

Create a file named dropbox_prompt.groovy

	import com.workrefined.dropbox.DropboxConfigForm;
	
	@Component
	@Grab(group='com.workrefined', module='dropbox-boot', version='0.1.0')
	public class MainController implements CommandLineRunner {
	 
		@Autowired DropboxConfigForm form
		
		public void run(String... args) throws Exception {
			form.prompt()
		}
	}

Now run it, using

	spring run dropbox_prompt.groovy
	
It will look something like this

	
	
	  .   ____          _            __ _ _
	 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
	( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
	 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
	  '  |____| .__|_| |_|_| |_\__, | / / / /
	 =========|_|==============|___/=/_/_/_/
	 :: Spring Boot ::        (v1.2.3.RELEASE)
	....
	How should this app identify itself to Dropbox?
	My App
	Enter your app key
	XXXXXXXXXX
	Enter your app secrete
	YYYYYYYYYY
	Now go to: 
		https://www.dropbox.com/1/oauth2/authorize?locale=en_US&client_id=XYXYXYXYX&response_type=code
	(you might have to login first) then click "allow"2. Click "Allow"
	Enter the authcode here
	ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ
	Excellent! You linked account: Ben Johnson
	Here is a fragment of values you can enter into your application.properties file
	########
	com.workrefined.dropbox.clientIdentifier=My App
	com.workrefined.dropbox.appKey=XXXXXXXXXX
	com.workrefined.dropbox.appSecret=YYYYYYYYYY
	com.workrefined.dropbox.accessToken=AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	#########

Save the following file to 
Install the Spring Boot CLI

http://docs.spring.io/spring-boot/docs/1.2.3.RELEASE/reference/htmlsingle/#getting-started-installing-the-cli