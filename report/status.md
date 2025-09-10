FAILED

<pre>Reporter class biz.lermitage.sub.service.dreport.impl.JetBrainsUCReporterImpl failed, ignoring
java.lang.NullPointerException: Cannot invoke "biz.lermitage.sub.model.jetbrains.JetBrainsApiResponse.getReleases()" because "apiResponse" is null
	at biz.lermitage.sub.service.dreport.impl.JetBrainsUCReporterImpl.write(JetBrainsUCReporterImpl.kt:43)
	at biz.lermitage.sub.service.dreport.impl.JetBrainsUCReporterImpl.generate(JetBrainsUCReporterImpl.kt:28)
	at biz.lermitage.sub.Application.run(Application.kt:89)
	at org.springframework.boot.SpringApplication.lambda$callRunner$5(SpringApplication.java:789)
	at org.springframework.util.function.ThrowingConsumer$1.acceptWithException(ThrowingConsumer.java:82)
	at org.springframework.util.function.ThrowingConsumer.accept(ThrowingConsumer.java:60)
	at org.springframework.util.function.ThrowingConsumer$1.accept(ThrowingConsumer.java:86)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:797)
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:788)
	at org.springframework.boot.SpringApplication.lambda$callRunners$3(SpringApplication.java:773)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
	at java.base/java.util.stream.SortedOps$SizedRefSortingSink.end(SortedOps.java:357)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:773)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:325)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1362)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1351)
	at biz.lermitage.sub.ApplicationKt.main(Application.kt:143)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:102)
	at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:64)
	at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:40)

</pre>