module com.greeting {
    exports spi;
    provides spi.Greeting
            with provider.GreetingEn,
                 provider.GreetingFr;
}