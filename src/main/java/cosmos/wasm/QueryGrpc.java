package cosmos.wasm;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * Query provides defines the gRPC querier service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: wasm/query.proto")
public class QueryGrpc {

  private QueryGrpc() {}

  public static final String SERVICE_NAME = "cosmos.wasm.Query";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<QueryOuterClass.QueryContractInfoRequest,
      QueryOuterClass.QueryContractInfoResponse> METHOD_CONTRACT_INFO =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmos.wasm.Query", "ContractInfo"),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryContractInfoRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryContractInfoResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<QueryOuterClass.QueryAllContractStateRequest,
      QueryOuterClass.QueryAllContractStateResponse> METHOD_ALL_CONTRACT_STATE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmos.wasm.Query", "AllContractState"),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryAllContractStateRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QueryAllContractStateResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<QueryOuterClass.QuerySmartContractStateRequest,
      QueryOuterClass.QuerySmartContractStateResponse> METHOD_SMART_CONTRACT_STATE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmos.wasm.Query", "SmartContractState"),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QuerySmartContractStateRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(QueryOuterClass.QuerySmartContractStateResponse.getDefaultInstance()));

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
   * Query provides defines the gRPC querier service
   * </pre>
   */
  public static abstract class QueryImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * ContractInfo gets the contract meta data
     * </pre>
     */
    public void contractInfo(QueryOuterClass.QueryContractInfoRequest request,
                             io.grpc.stub.StreamObserver<QueryOuterClass.QueryContractInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONTRACT_INFO, responseObserver);
    }

    /**
     * <pre>
     * AllContractState gets all raw store data for a single contract
     * </pre>
     */
    public void allContractState(QueryOuterClass.QueryAllContractStateRequest request,
                                 io.grpc.stub.StreamObserver<QueryOuterClass.QueryAllContractStateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ALL_CONTRACT_STATE, responseObserver);
    }

    /**
     * <pre>
     * SmartContractState get smart query result from the contract
     * </pre>
     */
    public void smartContractState(QueryOuterClass.QuerySmartContractStateRequest request,
                                   io.grpc.stub.StreamObserver<QueryOuterClass.QuerySmartContractStateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SMART_CONTRACT_STATE, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CONTRACT_INFO,
            asyncUnaryCall(
              new MethodHandlers<
                QueryOuterClass.QueryContractInfoRequest,
                QueryOuterClass.QueryContractInfoResponse>(
                  this, METHODID_CONTRACT_INFO)))
          .addMethod(
            METHOD_ALL_CONTRACT_STATE,
            asyncUnaryCall(
              new MethodHandlers<
                QueryOuterClass.QueryAllContractStateRequest,
                QueryOuterClass.QueryAllContractStateResponse>(
                  this, METHODID_ALL_CONTRACT_STATE)))
          .addMethod(
            METHOD_SMART_CONTRACT_STATE,
            asyncUnaryCall(
              new MethodHandlers<
                QueryOuterClass.QuerySmartContractStateRequest,
                QueryOuterClass.QuerySmartContractStateResponse>(
                  this, METHODID_SMART_CONTRACT_STATE)))
          .build();
    }
  }

  /**
   * <pre>
   * Query provides defines the gRPC querier service
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
     * ContractInfo gets the contract meta data
     * </pre>
     */
    public void contractInfo(QueryOuterClass.QueryContractInfoRequest request,
                             io.grpc.stub.StreamObserver<QueryOuterClass.QueryContractInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONTRACT_INFO, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * AllContractState gets all raw store data for a single contract
     * </pre>
     */
    public void allContractState(QueryOuterClass.QueryAllContractStateRequest request,
                                 io.grpc.stub.StreamObserver<QueryOuterClass.QueryAllContractStateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ALL_CONTRACT_STATE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * SmartContractState get smart query result from the contract
     * </pre>
     */
    public void smartContractState(QueryOuterClass.QuerySmartContractStateRequest request,
                                   io.grpc.stub.StreamObserver<QueryOuterClass.QuerySmartContractStateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SMART_CONTRACT_STATE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Query provides defines the gRPC querier service
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
     * ContractInfo gets the contract meta data
     * </pre>
     */
    public QueryOuterClass.QueryContractInfoResponse contractInfo(QueryOuterClass.QueryContractInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONTRACT_INFO, getCallOptions(), request);
    }

    /**
     * <pre>
     * AllContractState gets all raw store data for a single contract
     * </pre>
     */
    public QueryOuterClass.QueryAllContractStateResponse allContractState(QueryOuterClass.QueryAllContractStateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ALL_CONTRACT_STATE, getCallOptions(), request);
    }

    /**
     * <pre>
     * SmartContractState get smart query result from the contract
     * </pre>
     */
    public QueryOuterClass.QuerySmartContractStateResponse smartContractState(QueryOuterClass.QuerySmartContractStateRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SMART_CONTRACT_STATE, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Query provides defines the gRPC querier service
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
     * ContractInfo gets the contract meta data
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<QueryOuterClass.QueryContractInfoResponse> contractInfo(
        QueryOuterClass.QueryContractInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONTRACT_INFO, getCallOptions()), request);
    }

    /**
     * <pre>
     * AllContractState gets all raw store data for a single contract
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<QueryOuterClass.QueryAllContractStateResponse> allContractState(
        QueryOuterClass.QueryAllContractStateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ALL_CONTRACT_STATE, getCallOptions()), request);
    }

    /**
     * <pre>
     * SmartContractState get smart query result from the contract
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<QueryOuterClass.QuerySmartContractStateResponse> smartContractState(
        QueryOuterClass.QuerySmartContractStateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SMART_CONTRACT_STATE, getCallOptions()), request);
    }
  }

  private static final int METHODID_CONTRACT_INFO = 0;
  private static final int METHODID_ALL_CONTRACT_STATE = 1;
  private static final int METHODID_SMART_CONTRACT_STATE = 2;

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
        case METHODID_CONTRACT_INFO:
          serviceImpl.contractInfo((QueryOuterClass.QueryContractInfoRequest) request,
              (io.grpc.stub.StreamObserver<QueryOuterClass.QueryContractInfoResponse>) responseObserver);
          break;
        case METHODID_ALL_CONTRACT_STATE:
          serviceImpl.allContractState((QueryOuterClass.QueryAllContractStateRequest) request,
              (io.grpc.stub.StreamObserver<QueryOuterClass.QueryAllContractStateResponse>) responseObserver);
          break;
        case METHODID_SMART_CONTRACT_STATE:
          serviceImpl.smartContractState((QueryOuterClass.QuerySmartContractStateRequest) request,
              (io.grpc.stub.StreamObserver<QueryOuterClass.QuerySmartContractStateResponse>) responseObserver);
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
        METHOD_CONTRACT_INFO,
        METHOD_ALL_CONTRACT_STATE,
        METHOD_SMART_CONTRACT_STATE);
  }

}
