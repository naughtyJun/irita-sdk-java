package irita.sdk.client;

import io.grpc.*;

public class GrpcClientInterceptor implements ClientInterceptor {
    private final String projectKey;
    private final Metadata.Key<String> token = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);

    public GrpcClientInterceptor(String projectKey) {
        this.projectKey = projectKey;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        headers.put(token, projectKey); // TODO
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}
