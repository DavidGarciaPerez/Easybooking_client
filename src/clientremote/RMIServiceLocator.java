package clientremote;

import services.ILoginService;
import services.IVueloService;

public class RMIServiceLocator {

	// The Cache
	private ILoginService loginService;
	private IVueloService vueloService;

	public RMIServiceLocator() {

	}

	public void setService(String[] args) {
		try {
			setService(args, "login");
			setService(args, "vuelo");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void setService(String[] args, String service) {
		if (args.length != 3) {
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			if (service.equals("login")) {
				this.loginService = (ILoginService) java.rmi.Naming.lookup(name);
			}
			if (service.equals("vuelo")) {
				this.vueloService = (IVueloService) java.rmi.Naming.lookup(name);
			}

			System.out.println("- RMIServiceLocator '" + name + "' active...");
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
}
