module message.impl {
    requires message.api;
    exports impl;
    provides api.Message
            with impl.EmailMessage, impl.SNSMessage;
}