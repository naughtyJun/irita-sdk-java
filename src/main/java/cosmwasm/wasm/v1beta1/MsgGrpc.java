package cosmwasm.wasm.v1beta1;

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
 * Msg defines the wasm Msg service.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: x/wasm/internal/types/tx.proto")
public class MsgGrpc {

  private MsgGrpc() {}

  public static final String SERVICE_NAME = "cosmwasm.wasm.v1beta1.Msg";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Tx.MsgStoreCode,
      Tx.MsgStoreCodeResponse> METHOD_STORE_CODE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmwasm.wasm.v1beta1.Msg", "StoreCode"),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgStoreCode.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgStoreCodeResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Tx.MsgInstantiateContract,
      Tx.MsgInstantiateContractResponse> METHOD_INSTANTIATE_CONTRACT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmwasm.wasm.v1beta1.Msg", "InstantiateContract"),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgInstantiateContract.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgInstantiateContractResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Tx.MsgExecuteContract,
      Tx.MsgExecuteContractResponse> METHOD_EXECUTE_CONTRACT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmwasm.wasm.v1beta1.Msg", "ExecuteContract"),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgExecuteContract.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgExecuteContractResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Tx.MsgMigrateContract,
      Tx.MsgMigrateContractResponse> METHOD_MIGRATE_CONTRACT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmwasm.wasm.v1beta1.Msg", "MigrateContract"),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgMigrateContract.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgMigrateContractResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Tx.MsgUpdateAdmin,
      Tx.MsgUpdateAdminResponse> METHOD_UPDATE_ADMIN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmwasm.wasm.v1beta1.Msg", "UpdateAdmin"),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgUpdateAdmin.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgUpdateAdminResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Tx.MsgClearAdmin,
      Tx.MsgClearAdminResponse> METHOD_CLEAR_ADMIN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "cosmwasm.wasm.v1beta1.Msg", "ClearAdmin"),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgClearAdmin.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Tx.MsgClearAdminResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MsgStub newStub(io.grpc.Channel channel) {
    return new MsgStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MsgBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MsgBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static MsgFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MsgFutureStub(channel);
  }

  /**
   * <pre>
   * Msg defines the wasm Msg service.
   * </pre>
   */
  public static abstract class MsgImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * StoreCode to submit Wasm code to the system
     * </pre>
     */
    public void storeCode(Tx.MsgStoreCode request,
                          io.grpc.stub.StreamObserver<Tx.MsgStoreCodeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_STORE_CODE, responseObserver);
    }

    /**
     * <pre>
     *  Instantiate creates a new smart contract instance for the given code id.
     * </pre>
     */
    public void instantiateContract(Tx.MsgInstantiateContract request,
                                    io.grpc.stub.StreamObserver<Tx.MsgInstantiateContractResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_INSTANTIATE_CONTRACT, responseObserver);
    }

    /**
     * <pre>
     * Execute submits the given message data to a smart contract
     * </pre>
     */
    public void executeContract(Tx.MsgExecuteContract request,
                                io.grpc.stub.StreamObserver<Tx.MsgExecuteContractResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EXECUTE_CONTRACT, responseObserver);
    }

    /**
     * <pre>
     * Migrate runs a code upgrade/ downgrade for a smart contract
     * </pre>
     */
    public void migrateContract(Tx.MsgMigrateContract request,
                                io.grpc.stub.StreamObserver<Tx.MsgMigrateContractResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MIGRATE_CONTRACT, responseObserver);
    }

    /**
     * <pre>
     * UpdateAdmin sets a new   admin for a smart contract
     * </pre>
     */
    public void updateAdmin(Tx.MsgUpdateAdmin request,
                            io.grpc.stub.StreamObserver<Tx.MsgUpdateAdminResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_ADMIN, responseObserver);
    }

    /**
     * <pre>
     * ClearAdmin removes any admin stored for a smart contract
     * </pre>
     */
    public void clearAdmin(Tx.MsgClearAdmin request,
                           io.grpc.stub.StreamObserver<Tx.MsgClearAdminResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CLEAR_ADMIN, responseObserver);
    }

    @Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_STORE_CODE,
            asyncUnaryCall(
              new MethodHandlers<
                Tx.MsgStoreCode,
                Tx.MsgStoreCodeResponse>(
                  this, METHODID_STORE_CODE)))
          .addMethod(
            METHOD_INSTANTIATE_CONTRACT,
            asyncUnaryCall(
              new MethodHandlers<
                Tx.MsgInstantiateContract,
                Tx.MsgInstantiateContractResponse>(
                  this, METHODID_INSTANTIATE_CONTRACT)))
          .addMethod(
            METHOD_EXECUTE_CONTRACT,
            asyncUnaryCall(
              new MethodHandlers<
                Tx.MsgExecuteContract,
                Tx.MsgExecuteContractResponse>(
                  this, METHODID_EXECUTE_CONTRACT)))
          .addMethod(
            METHOD_MIGRATE_CONTRACT,
            asyncUnaryCall(
              new MethodHandlers<
                Tx.MsgMigrateContract,
                Tx.MsgMigrateContractResponse>(
                  this, METHODID_MIGRATE_CONTRACT)))
          .addMethod(
            METHOD_UPDATE_ADMIN,
            asyncUnaryCall(
              new MethodHandlers<
                Tx.MsgUpdateAdmin,
                Tx.MsgUpdateAdminResponse>(
                  this, METHODID_UPDATE_ADMIN)))
          .addMethod(
            METHOD_CLEAR_ADMIN,
            asyncUnaryCall(
              new MethodHandlers<
                Tx.MsgClearAdmin,
                Tx.MsgClearAdminResponse>(
                  this, METHODID_CLEAR_ADMIN)))
          .build();
    }
  }

  /**
   * <pre>
   * Msg defines the wasm Msg service.
   * </pre>
   */
  public static final class MsgStub extends io.grpc.stub.AbstractStub<MsgStub> {
    private MsgStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MsgStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MsgStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MsgStub(channel, callOptions);
    }

    /**
     * <pre>
     * StoreCode to submit Wasm code to the system
     * </pre>
     */
    public void storeCode(Tx.MsgStoreCode request,
                          io.grpc.stub.StreamObserver<Tx.MsgStoreCodeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_STORE_CODE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *  Instantiate creates a new smart contract instance for the given code id.
     * </pre>
     */
    public void instantiateContract(Tx.MsgInstantiateContract request,
                                    io.grpc.stub.StreamObserver<Tx.MsgInstantiateContractResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INSTANTIATE_CONTRACT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Execute submits the given message data to a smart contract
     * </pre>
     */
    public void executeContract(Tx.MsgExecuteContract request,
                                io.grpc.stub.StreamObserver<Tx.MsgExecuteContractResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EXECUTE_CONTRACT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Migrate runs a code upgrade/ downgrade for a smart contract
     * </pre>
     */
    public void migrateContract(Tx.MsgMigrateContract request,
                                io.grpc.stub.StreamObserver<Tx.MsgMigrateContractResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MIGRATE_CONTRACT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * UpdateAdmin sets a new   admin for a smart contract
     * </pre>
     */
    public void updateAdmin(Tx.MsgUpdateAdmin request,
                            io.grpc.stub.StreamObserver<Tx.MsgUpdateAdminResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_ADMIN, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ClearAdmin removes any admin stored for a smart contract
     * </pre>
     */
    public void clearAdmin(Tx.MsgClearAdmin request,
                           io.grpc.stub.StreamObserver<Tx.MsgClearAdminResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CLEAR_ADMIN, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Msg defines the wasm Msg service.
   * </pre>
   */
  public static final class MsgBlockingStub extends io.grpc.stub.AbstractStub<MsgBlockingStub> {
    private MsgBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MsgBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MsgBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MsgBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * StoreCode to submit Wasm code to the system
     * </pre>
     */
    public Tx.MsgStoreCodeResponse storeCode(Tx.MsgStoreCode request) {
      return blockingUnaryCall(
          getChannel(), METHOD_STORE_CODE, getCallOptions(), request);
    }

    /**
     * <pre>
     *  Instantiate creates a new smart contract instance for the given code id.
     * </pre>
     */
    public Tx.MsgInstantiateContractResponse instantiateContract(Tx.MsgInstantiateContract request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INSTANTIATE_CONTRACT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Execute submits the given message data to a smart contract
     * </pre>
     */
    public Tx.MsgExecuteContractResponse executeContract(Tx.MsgExecuteContract request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EXECUTE_CONTRACT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Migrate runs a code upgrade/ downgrade for a smart contract
     * </pre>
     */
    public Tx.MsgMigrateContractResponse migrateContract(Tx.MsgMigrateContract request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MIGRATE_CONTRACT, getCallOptions(), request);
    }

    /**
     * <pre>
     * UpdateAdmin sets a new   admin for a smart contract
     * </pre>
     */
    public Tx.MsgUpdateAdminResponse updateAdmin(Tx.MsgUpdateAdmin request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_ADMIN, getCallOptions(), request);
    }

    /**
     * <pre>
     * ClearAdmin removes any admin stored for a smart contract
     * </pre>
     */
    public Tx.MsgClearAdminResponse clearAdmin(Tx.MsgClearAdmin request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CLEAR_ADMIN, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Msg defines the wasm Msg service.
   * </pre>
   */
  public static final class MsgFutureStub extends io.grpc.stub.AbstractStub<MsgFutureStub> {
    private MsgFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MsgFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected MsgFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MsgFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * StoreCode to submit Wasm code to the system
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Tx.MsgStoreCodeResponse> storeCode(
        Tx.MsgStoreCode request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_STORE_CODE, getCallOptions()), request);
    }

    /**
     * <pre>
     *  Instantiate creates a new smart contract instance for the given code id.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Tx.MsgInstantiateContractResponse> instantiateContract(
        Tx.MsgInstantiateContract request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INSTANTIATE_CONTRACT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Execute submits the given message data to a smart contract
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Tx.MsgExecuteContractResponse> executeContract(
        Tx.MsgExecuteContract request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EXECUTE_CONTRACT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Migrate runs a code upgrade/ downgrade for a smart contract
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Tx.MsgMigrateContractResponse> migrateContract(
        Tx.MsgMigrateContract request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MIGRATE_CONTRACT, getCallOptions()), request);
    }

    /**
     * <pre>
     * UpdateAdmin sets a new   admin for a smart contract
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Tx.MsgUpdateAdminResponse> updateAdmin(
        Tx.MsgUpdateAdmin request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_ADMIN, getCallOptions()), request);
    }

    /**
     * <pre>
     * ClearAdmin removes any admin stored for a smart contract
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<Tx.MsgClearAdminResponse> clearAdmin(
        Tx.MsgClearAdmin request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CLEAR_ADMIN, getCallOptions()), request);
    }
  }

  private static final int METHODID_STORE_CODE = 0;
  private static final int METHODID_INSTANTIATE_CONTRACT = 1;
  private static final int METHODID_EXECUTE_CONTRACT = 2;
  private static final int METHODID_MIGRATE_CONTRACT = 3;
  private static final int METHODID_UPDATE_ADMIN = 4;
  private static final int METHODID_CLEAR_ADMIN = 5;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MsgImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(MsgImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STORE_CODE:
          serviceImpl.storeCode((Tx.MsgStoreCode) request,
              (io.grpc.stub.StreamObserver<Tx.MsgStoreCodeResponse>) responseObserver);
          break;
        case METHODID_INSTANTIATE_CONTRACT:
          serviceImpl.instantiateContract((Tx.MsgInstantiateContract) request,
              (io.grpc.stub.StreamObserver<Tx.MsgInstantiateContractResponse>) responseObserver);
          break;
        case METHODID_EXECUTE_CONTRACT:
          serviceImpl.executeContract((Tx.MsgExecuteContract) request,
              (io.grpc.stub.StreamObserver<Tx.MsgExecuteContractResponse>) responseObserver);
          break;
        case METHODID_MIGRATE_CONTRACT:
          serviceImpl.migrateContract((Tx.MsgMigrateContract) request,
              (io.grpc.stub.StreamObserver<Tx.MsgMigrateContractResponse>) responseObserver);
          break;
        case METHODID_UPDATE_ADMIN:
          serviceImpl.updateAdmin((Tx.MsgUpdateAdmin) request,
              (io.grpc.stub.StreamObserver<Tx.MsgUpdateAdminResponse>) responseObserver);
          break;
        case METHODID_CLEAR_ADMIN:
          serviceImpl.clearAdmin((Tx.MsgClearAdmin) request,
              (io.grpc.stub.StreamObserver<Tx.MsgClearAdminResponse>) responseObserver);
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
        METHOD_STORE_CODE,
        METHOD_INSTANTIATE_CONTRACT,
        METHOD_EXECUTE_CONTRACT,
        METHOD_MIGRATE_CONTRACT,
        METHOD_UPDATE_ADMIN,
        METHOD_CLEAR_ADMIN);
  }

}
