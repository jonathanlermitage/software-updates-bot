FAILED

<pre>checker class biz.lermitage.sub.service.checker.impl.Python3WindowsChecker failed, ignoring
java.util.NoSuchElementException: No value present
	at java.base/java.util.Optional.get(Optional.java:148)
	at biz.lermitage.sub.service.checker.impl.PythonChecker.check(PythonChecker.kt:30)
	at biz.lermitage.sub.Application.run(Application.kt:52)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:806)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:790)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:333)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1313)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302)
	at biz.lermitage.sub.ApplicationKt.main(Application.kt:115)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:107)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:88)

checker class biz.lermitage.sub.service.checker.impl.SevenTTChecker failed, ignoring
java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
	at java.base/java.util.Objects.checkIndex(Objects.java:372)
	at java.base/java.util.ArrayList.get(ArrayList.java:459)
	at biz.lermitage.sub.service.checker.impl.SevenTTChecker.check(SevenTTChecker.kt:22)
	at biz.lermitage.sub.Application.run(Application.kt:52)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:806)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:790)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:333)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1313)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302)
	at biz.lermitage.sub.ApplicationKt.main(Application.kt:115)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:107)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:88)

</pre>