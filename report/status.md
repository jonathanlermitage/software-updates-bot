FAILED

<pre>checker class biz.lermitage.sub.service.checker.impl.AdoptOpenJDK11LinuxChecker failed, ignoring
org.jsoup.HttpStatusException: HTTP error fetching URL. Status=520, URL=https://api.adoptopenjdk.net/v3/assets/latest/11/hotspot?release=latest&jvm_impl=hotspot&vendor=adoptopenjdk&
	at org.jsoup.helper.HttpConnection$Response.execute(HttpConnection.java:762)
	at org.jsoup.helper.HttpConnection$Response.execute(HttpConnection.java:707)
	at org.jsoup.helper.HttpConnection.execute(HttpConnection.java:297)
	at org.jsoup.helper.HttpConnection.get(HttpConnection.java:286)
	at biz.lermitage.sub.service.scrapper.impl.ScrapperImpl.fetchText(ScrapperImpl.kt:39)
	at biz.lermitage.sub.service.checker.impl.AdoptOpenJDKChecker.check(AdoptOpenJDKChecker.kt:28)
	at biz.lermitage.sub.SoftwareUpdatesBotApplication.run(Application.kt:49)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:795)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:779)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:322)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1237)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1226)
	at biz.lermitage.sub.ApplicationKt.main(Application.kt:111)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:109)
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:58)
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:88)

</pre>