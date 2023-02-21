FAILED

<pre>checker class biz.lermitage.sub.service.checker.impl.GoChecker failed, ignoring
java.lang.StringIndexOutOfBoundsException: begin 0, end -1, length 8
	at java.base/java.lang.String.checkBoundsBeginEnd(String.java:4602)
	at java.base/java.lang.String.substring(String.java:2705)
	at biz.lermitage.sub.service.checker.impl.GoChecker.check(GoChecker.kt:26)
	at biz.lermitage.sub.Application.run(Application.kt:54)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:767)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:751)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1291)
	at biz.lermitage.sub.ApplicationKt.main(Application.kt:130)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:95)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:65)

</pre>