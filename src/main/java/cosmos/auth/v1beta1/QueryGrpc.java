package cosmos.auth.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Query defines the gRPC querier service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: cosmos/auth/v1beta1/query.proto")
public class QueryGrpc {

  private QueryGrpc() {}

  public static final String SERVICE_NAME = "cosmos.auth.v1beta1.Query";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<QueryOuterClass.QueryAccountRequest,
      QueryOuterClass.QueryAccountResponse> METHOD_ACCOUNT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmos.auth.v1beta1.Query", "Account"),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryAccountRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryAccountResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<QueryOuterClass.QueryParamsRequest,
      QueryOuterClass.QueryParamsResponse> METHOD_PARAMS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmos.auth.v1beta1.Query", "Params"),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryParamsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryParamsResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QueryStub newStub(io.grpc.Channel channel) {
    return new QueryStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QueryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new QueryBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static QueryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new QueryFutureStub(channel);
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static abstract class QueryImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Account returns account details based on address.
     * </pre>
     */
    public void account(QueryOuterClass.QueryAccountRequest request,
                        io.grpc.stub.StreamObserver<QueryOuterClass.QueryAccountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ACCOUNT, responseObserver);
    }

    /**
     * <pre>
     * Params queries all parameters.
     * </pre>
     */
    public void params(QueryOuterClass.QueryParamsRequest request,
                       io.grpc.stub.StreamObserver<QueryOuterClass.QueryParamsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PARAMS, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ACCOUNT,
            asyncUnaryCall(
              new MethodHandlers<
                QueryOuterClass.QueryAccountRequest,
                QueryOuterClass.QueryAccountResponse>(
                  this, METHODID_ACCOUNT)))
          .addMethod(
            METHOD_PARAMS,
            asyncUnaryCall(
              new MethodHandlers<
                QueryOuterClass.QueryParamsRequest,
                QueryOuterClass.QueryParamsResponse>(
                  this, METHODID_PARAMS)))
          .build();
    }
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static final class QueryStub extends io.grpc.stub.AbstractStub<QueryStub> {
    private QueryStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QueryStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryStub(channel, callOptions);
    }

    /**
     * <pre>
     * Account returns account details based on address.
     * </pre>
     */
    public void account(QueryOuterClass.QueryAccountRequest request,
                        io.grpc.stub.StreamObserver<QueryOuterClass.QueryAccountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ACCOUNT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Params queries all parameters.
     * </pre>
     */
    public void params(QueryOuterClass.QueryParamsRequest request,
                       io.grpc.stub.StreamObserver<QueryOuterClass.QueryParamsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PARAMS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static final class QueryBlockingStub extends io.grpc.stub.AbstractStub<QueryBlockingStub> {
    private QueryBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QueryBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Account returns account details based on address.
     * </pre>
     */
    public QueryOuterClass.QueryAccountResponse account(QueryOuterClass.QueryAccountRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ACCOUNT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Params queries all parameters.
     * </pre>
     */
    public QueryOuterClass.QueryParamsResponse params(QueryOuterClass.QueryParamsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PARAMS, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Query defines the gRPC querier service.
   * </pre>
   */
  public static final class QueryFutureStub extends io.grpc.stub.AbstractStub<QueryFutureStub> {
    private QueryFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private QueryFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected QueryFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new QueryFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Account returns account details based on address.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<QueryOuterClass.QueryAccountResponse> account(
        QueryOuterClass.QueryAccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ACCOUNT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Params queries all parameters.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<QueryOuterClass.QueryParamsResponse> params(
        QueryOuterClass.QueryParamsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PARAMS, getCallOptions()), request);
    }
  }

  private static final int METHODID_ACCOUNT = 0;
  private static final int METHODID_PARAMS = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QueryImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(QueryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ACCOUNT:
          serviceImpl.account((QueryOuterClass.QueryAccountRequest) request,
              (io.grpc.stub.StreamObserver<QueryOuterClass.QueryAccountResponse>) responseObserver);
          break;
        case METHODID_PARAMS:
          serviceImpl.params((QueryOuterClass.QueryParamsRequest) request,
              (io.grpc.stub.StreamObserver<QueryOuterClass.QueryParamsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_ACCOUNT,
        METHOD_PARAMS);
  }

}
