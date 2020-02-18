package test;
import com.psi.subs.management.c.FileUploadedCommand;
import com.tlc.gui.modules.common.IView;
import com.tlc.gui.modules.common.PluginHeaders;

public class main {

	public static void main(String[] args) {
		int j = 123456;
		String x = "639507771003";
		x = x.substring(0, 5) + "-" + x.substring(5, x.length());
		System.out.println(x.replace("63", "0"));
		//test();	
		 
	}

	public static void test() {
		System.out.println("START");
		String data = "{\"Year\":\"2018\",\"Period\":\"2018-01\",\"Provider\":\"CBCI\",\"DateFrom\":\"2018-01-01\",\"DateTo\":\"2018-01-31\"}";
		JsonRequest json = new JsonRequest(data);
		PluginHeaders h = new PluginHeaders();

		FileUploadedCommand pndgmessage = new FileUploadedCommand();
		pndgmessage.setRequest(json);
		pndgmessage.setHeaders(h);

		IView v = pndgmessage.execute();
		System.out.println(v.render());
		System.out.println("END");
	}

}
