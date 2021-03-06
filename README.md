DIO
===

Named towards the greatest heavy metal vocalist of all time - Ronnie James Dio (RIP), Dio is a small memory and disk footprint Sinatra-inspired web framework for Java. 

Most Java web frameworks build on top of the Servlet specification in order to be standards-compliant. In today's commodity hosting world, running a Servlet container would not be possible on a 256MB VPS server, making Java applications not suitable for small web applications, self-funded technology start-ups or anyone in a budget to host in the web. Dio aims at bridging that gap, having a memory footprint of 32MB, with an even smaller disk footprint (4k anyone?)

DIO provides it's own multi-threaded web server, is capable of serving regular files, and routing requests to controller classes that implement the DioController REST-friendly interface.

Sinatra is only but an inspiration to DIO's design, and it's not the project's design goal to be merely a Sinatra clone in Java. The conventions are more inspired by Rails than Sinatra, although all that is possible in Sinatra is possible in DIO.

The project is still in a very early stage, so DO expect the API to change drastically in the upcoming months.

Using DIO
---------

1. Configure the package where your Controllers will be found at DioConfig:

         public static String basePackage = "com.alexmreis.dio.test";

2. Create your controllers implementing DioController:

		public class Test implements DioController{
		    public String get(Map<String, String> parameters) {
		        return "<html><body><h1>Hello world!</h1></body></html>";
		    }

		    public String put(Map<String, String> parameters) {
		        return null;
		    }

		    public String delete(Map<String, String> parameters) {
		        return null;
		    }

		    public String post(Map<String, String> parameters) {
		        return null;
		    }
		}
3. Add your public assets ( CSS, JavaScript, Images, HTML files) to the public folder

4. Start the DIO server:

		java com.alexmreis.dio.DioApplication

5. Point your browser to http://localhost:3000/[controller_name]
		
		http://localhost:3000/test

6. Enjoy non-painful, low memory footprint Java web development!