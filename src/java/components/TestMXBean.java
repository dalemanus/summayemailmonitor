package components;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

public class TestMXBean {

	public static void main(String[] args) {
		OperatingSystemMXBean osBean =
				ManagementFactory.getOperatingSystemMXBean();
		
		
		System.out.println("Arch: " + osBean.getArch());
		
		System.out.println("Processors: " + osBean.getAvailableProcessors());
		System.out.println("OS: " + osBean.getName());
		System.out.println("Load Average: " + osBean.getSystemLoadAverage());
		System.out.println("OS Version: " + osBean.getVersion());
		
		MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
		
		System.out.println("" + mxBean.getHeapMemoryUsage());
		System.out.println("");
	}
}
