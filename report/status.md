FAILED

<pre>checker class biz.lermitage.sub.service.checker.impl.LesCastCodeursChecker failed, ignoring
java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
	at java.base/java.util.Objects.checkIndex(Objects.java:372)
	at java.base/java.util.ArrayList.get(ArrayList.java:459)
	at biz.lermitage.sub.service.checker.impl.LesCastCodeursChecker.check(LesCastCodeursChecker.kt:21)
	at biz.lermitage.sub.Application.run(Application.kt:50)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:795)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:779)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:322)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1237)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226)
	at biz.lermitage.sub.ApplicationKt.main(Application.kt:113)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:107)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:88)

</pre>