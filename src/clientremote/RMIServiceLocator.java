package clientremote;

import services.ILoginService;
import services.IPagoService;
import services.IVueloService;

public class RMIServiceLocator {

	// The Cache
	private ILoginService loginService;
	private IVueloService vueloService;
	private IPagoService pagoService;
	private String name;

	public RMIServiceLocator(String[] args) {
		this.setServices(args);
	}

	public void setServices(String[] args) {
		if (args.length != 5) {
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
				name = "//" + args[0] + ":" + args[1] + "/" + args[2];
				this.loginService = (ILoginService) java.rmi.Naming.lookup(name);
				name = "//" + args[0] + ":" + args[1] + "/" + args[3];
				this.vueloService = (IVueloService) java.rmi.Naming.lookup(name);
				name = "//" + args[0] + ":" + args[1] + "/" + args[4];
				this.pagoService = (IPagoService) java.rmi.Naming.lookup(name);
			
			System.out.println("- RMIServiceLocator active...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public IVueloService getVueloService() {
		return vueloService;
	}
	
	public IPagoService getPagoService() {
		return pagoService;
	}
}
