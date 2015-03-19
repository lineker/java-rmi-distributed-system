
package rm.rmwebservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rm.rmwebservice.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteCarsResponse_QNAME = new QName("http://webservice.rm/", "deleteCarsResponse");
    private final static QName _QueryFlightPriceResponse_QNAME = new QName("http://webservice.rm/", "queryFlightPriceResponse");
    private final static QName _CustomerReserveWrite_QNAME = new QName("http://webservice.rm/", "customerReserveWrite");
    private final static QName _NewCustomerWithIdResponse_QNAME = new QName("http://webservice.rm/", "newCustomerWithIdResponse");
    private final static QName _RealShutdownResponse_QNAME = new QName("http://webservice.rm/", "realShutdownResponse");
    private final static QName _AddCars_QNAME = new QName("http://webservice.rm/", "addCars");
    private final static QName _QueryRoomsPriceResponse_QNAME = new QName("http://webservice.rm/", "queryRoomsPriceResponse");
    private final static QName _ItineraryResponse_QNAME = new QName("http://webservice.rm/", "itineraryResponse");
    private final static QName _RemoveReservationResponse_QNAME = new QName("http://webservice.rm/", "removeReservationResponse");
    private final static QName _StartWithId_QNAME = new QName("http://webservice.rm/", "startWithId");
    private final static QName _InvalidTransactionException_QNAME = new QName("http://webservice.rm/", "InvalidTransactionException");
    private final static QName _DumpResponse_QNAME = new QName("http://webservice.rm/", "dumpResponse");
    private final static QName _QueryCustomerInfo_QNAME = new QName("http://webservice.rm/", "queryCustomerInfo");
    private final static QName _Commit_QNAME = new QName("http://webservice.rm/", "commit");
    private final static QName _ReserveFlight_QNAME = new QName("http://webservice.rm/", "reserveFlight");
    private final static QName _ReserveRoomResponse_QNAME = new QName("http://webservice.rm/", "reserveRoomResponse");
    private final static QName _QueryCustomerInfoResponse_QNAME = new QName("http://webservice.rm/", "queryCustomerInfoResponse");
    private final static QName _QueryRoomsResponse_QNAME = new QName("http://webservice.rm/", "queryRoomsResponse");
    private final static QName _RemoveReservation_QNAME = new QName("http://webservice.rm/", "removeReservation");
    private final static QName _QueryRooms_QNAME = new QName("http://webservice.rm/", "queryRooms");
    private final static QName _CustomerExistResponse_QNAME = new QName("http://webservice.rm/", "customerExistResponse");
    private final static QName _NewCustomerResponse_QNAME = new QName("http://webservice.rm/", "newCustomerResponse");
    private final static QName _DeadlockException_QNAME = new QName("http://webservice.rm/", "DeadlockException");
    private final static QName _DeleteFlightResponse_QNAME = new QName("http://webservice.rm/", "deleteFlightResponse");
    private final static QName _AddFlightResponse_QNAME = new QName("http://webservice.rm/", "addFlightResponse");
    private final static QName _ReserveCarResponse_QNAME = new QName("http://webservice.rm/", "reserveCarResponse");
    private final static QName _ReserveCar_QNAME = new QName("http://webservice.rm/", "reserveCar");
    private final static QName _QueryFlightPrice_QNAME = new QName("http://webservice.rm/", "queryFlightPrice");
    private final static QName _QueryFlightResponse_QNAME = new QName("http://webservice.rm/", "queryFlightResponse");
    private final static QName _AddCarsResponse_QNAME = new QName("http://webservice.rm/", "addCarsResponse");
    private final static QName _AbortResponse_QNAME = new QName("http://webservice.rm/", "abortResponse");
    private final static QName _Start_QNAME = new QName("http://webservice.rm/", "start");
    private final static QName _NewCustomerWithId_QNAME = new QName("http://webservice.rm/", "newCustomerWithId");
    private final static QName _QueryCarsPrice_QNAME = new QName("http://webservice.rm/", "queryCarsPrice");
    private final static QName _DeleteCustomerResponse_QNAME = new QName("http://webservice.rm/", "deleteCustomerResponse");
    private final static QName _DeleteCustomer_QNAME = new QName("http://webservice.rm/", "deleteCustomer");
    private final static QName _QueryRoomsPrice_QNAME = new QName("http://webservice.rm/", "queryRoomsPrice");
    private final static QName _Dump_QNAME = new QName("http://webservice.rm/", "dump");
    private final static QName _QueryCarsResponse_QNAME = new QName("http://webservice.rm/", "queryCarsResponse");
    private final static QName _StartResponse_QNAME = new QName("http://webservice.rm/", "startResponse");
    private final static QName _AddRoomsResponse_QNAME = new QName("http://webservice.rm/", "addRoomsResponse");
    private final static QName _StartWithIdResponse_QNAME = new QName("http://webservice.rm/", "startWithIdResponse");
    private final static QName _QueryFlight_QNAME = new QName("http://webservice.rm/", "queryFlight");
    private final static QName _QueryCarsPriceResponse_QNAME = new QName("http://webservice.rm/", "queryCarsPriceResponse");
    private final static QName _ReserveFlightResponse_QNAME = new QName("http://webservice.rm/", "reserveFlightResponse");
    private final static QName _QueryCars_QNAME = new QName("http://webservice.rm/", "queryCars");
    private final static QName _ReserveRoom_QNAME = new QName("http://webservice.rm/", "reserveRoom");
    private final static QName _NumberFormatException_QNAME = new QName("http://webservice.rm/", "NumberFormatException");
    private final static QName _Itinerary_QNAME = new QName("http://webservice.rm/", "itinerary");
    private final static QName _ShutdownResponse_QNAME = new QName("http://webservice.rm/", "shutdownResponse");
    private final static QName _AddFlight_QNAME = new QName("http://webservice.rm/", "addFlight");
    private final static QName _Shutdown_QNAME = new QName("http://webservice.rm/", "shutdown");
    private final static QName _CustomerExist_QNAME = new QName("http://webservice.rm/", "customerExist");
    private final static QName _CustomerReserveWriteResponse_QNAME = new QName("http://webservice.rm/", "customerReserveWriteResponse");
    private final static QName _AddRooms_QNAME = new QName("http://webservice.rm/", "addRooms");
    private final static QName _RealShutdown_QNAME = new QName("http://webservice.rm/", "realShutdown");
    private final static QName _CommitResponse_QNAME = new QName("http://webservice.rm/", "commitResponse");
    private final static QName _DeleteRoomsResponse_QNAME = new QName("http://webservice.rm/", "deleteRoomsResponse");
    private final static QName _NewCustomer_QNAME = new QName("http://webservice.rm/", "newCustomer");
    private final static QName _DeleteRooms_QNAME = new QName("http://webservice.rm/", "deleteRooms");
    private final static QName _Abort_QNAME = new QName("http://webservice.rm/", "abort");
    private final static QName _TransactionAbortedException_QNAME = new QName("http://webservice.rm/", "TransactionAbortedException");
    private final static QName _DeleteCars_QNAME = new QName("http://webservice.rm/", "deleteCars");
    private final static QName _DeleteFlight_QNAME = new QName("http://webservice.rm/", "deleteFlight");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rm.rmwebservice.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RealShutdown }
     * 
     */
    public RealShutdown createRealShutdown() {
        return new RealShutdown();
    }

    /**
     * Create an instance of {@link QueryCarsResponse }
     * 
     */
    public QueryCarsResponse createQueryCarsResponse() {
        return new QueryCarsResponse();
    }

    /**
     * Create an instance of {@link Commit }
     * 
     */
    public Commit createCommit() {
        return new Commit();
    }

    /**
     * Create an instance of {@link ReserveRoom }
     * 
     */
    public ReserveRoom createReserveRoom() {
        return new ReserveRoom();
    }

    /**
     * Create an instance of {@link StartResponse }
     * 
     */
    public StartResponse createStartResponse() {
        return new StartResponse();
    }

    /**
     * Create an instance of {@link StartWithId }
     * 
     */
    public StartWithId createStartWithId() {
        return new StartWithId();
    }

    /**
     * Create an instance of {@link AbortResponse }
     * 
     */
    public AbortResponse createAbortResponse() {
        return new AbortResponse();
    }

    /**
     * Create an instance of {@link ShutdownResponse }
     * 
     */
    public ShutdownResponse createShutdownResponse() {
        return new ShutdownResponse();
    }

    /**
     * Create an instance of {@link RemoveReservationResponse }
     * 
     */
    public RemoveReservationResponse createRemoveReservationResponse() {
        return new RemoveReservationResponse();
    }

    /**
     * Create an instance of {@link QueryRooms }
     * 
     */
    public QueryRooms createQueryRooms() {
        return new QueryRooms();
    }

    /**
     * Create an instance of {@link AddRoomsResponse }
     * 
     */
    public AddRoomsResponse createAddRoomsResponse() {
        return new AddRoomsResponse();
    }

    /**
     * Create an instance of {@link DeleteCustomer }
     * 
     */
    public DeleteCustomer createDeleteCustomer() {
        return new DeleteCustomer();
    }

    /**
     * Create an instance of {@link QueryCarsPrice }
     * 
     */
    public QueryCarsPrice createQueryCarsPrice() {
        return new QueryCarsPrice();
    }

    /**
     * Create an instance of {@link QueryFlightPrice }
     * 
     */
    public QueryFlightPrice createQueryFlightPrice() {
        return new QueryFlightPrice();
    }

    /**
     * Create an instance of {@link QueryRoomsPrice }
     * 
     */
    public QueryRoomsPrice createQueryRoomsPrice() {
        return new QueryRoomsPrice();
    }

    /**
     * Create an instance of {@link ReserveRoomResponse }
     * 
     */
    public ReserveRoomResponse createReserveRoomResponse() {
        return new ReserveRoomResponse();
    }

    /**
     * Create an instance of {@link NewCustomerResponse }
     * 
     */
    public NewCustomerResponse createNewCustomerResponse() {
        return new NewCustomerResponse();
    }

    /**
     * Create an instance of {@link CustomerExist }
     * 
     */
    public CustomerExist createCustomerExist() {
        return new CustomerExist();
    }

    /**
     * Create an instance of {@link DumpResponse }
     * 
     */
    public DumpResponse createDumpResponse() {
        return new DumpResponse();
    }

    /**
     * Create an instance of {@link ReserveCarResponse }
     * 
     */
    public ReserveCarResponse createReserveCarResponse() {
        return new ReserveCarResponse();
    }

    /**
     * Create an instance of {@link DeleteFlight }
     * 
     */
    public DeleteFlight createDeleteFlight() {
        return new DeleteFlight();
    }

    /**
     * Create an instance of {@link DeleteCars }
     * 
     */
    public DeleteCars createDeleteCars() {
        return new DeleteCars();
    }

    /**
     * Create an instance of {@link RemoveReservation }
     * 
     */
    public RemoveReservation createRemoveReservation() {
        return new RemoveReservation();
    }

    /**
     * Create an instance of {@link QueryFlightPriceResponse }
     * 
     */
    public QueryFlightPriceResponse createQueryFlightPriceResponse() {
        return new QueryFlightPriceResponse();
    }

    /**
     * Create an instance of {@link QueryRoomsPriceResponse }
     * 
     */
    public QueryRoomsPriceResponse createQueryRoomsPriceResponse() {
        return new QueryRoomsPriceResponse();
    }

    /**
     * Create an instance of {@link ReserveFlight }
     * 
     */
    public ReserveFlight createReserveFlight() {
        return new ReserveFlight();
    }

    /**
     * Create an instance of {@link DeleteCustomerResponse }
     * 
     */
    public DeleteCustomerResponse createDeleteCustomerResponse() {
        return new DeleteCustomerResponse();
    }

    /**
     * Create an instance of {@link DeadlockException }
     * 
     */
    public DeadlockException createDeadlockException() {
        return new DeadlockException();
    }

    /**
     * Create an instance of {@link Abort }
     * 
     */
    public Abort createAbort() {
        return new Abort();
    }

    /**
     * Create an instance of {@link Shutdown }
     * 
     */
    public Shutdown createShutdown() {
        return new Shutdown();
    }

    /**
     * Create an instance of {@link AddFlight }
     * 
     */
    public AddFlight createAddFlight() {
        return new AddFlight();
    }

    /**
     * Create an instance of {@link QueryCustomerInfo }
     * 
     */
    public QueryCustomerInfo createQueryCustomerInfo() {
        return new QueryCustomerInfo();
    }

    /**
     * Create an instance of {@link DeleteRooms }
     * 
     */
    public DeleteRooms createDeleteRooms() {
        return new DeleteRooms();
    }

    /**
     * Create an instance of {@link DeleteRoomsResponse }
     * 
     */
    public DeleteRoomsResponse createDeleteRoomsResponse() {
        return new DeleteRoomsResponse();
    }

    /**
     * Create an instance of {@link RealShutdownResponse }
     * 
     */
    public RealShutdownResponse createRealShutdownResponse() {
        return new RealShutdownResponse();
    }

    /**
     * Create an instance of {@link QueryFlightResponse }
     * 
     */
    public QueryFlightResponse createQueryFlightResponse() {
        return new QueryFlightResponse();
    }

    /**
     * Create an instance of {@link StartWithIdResponse }
     * 
     */
    public StartWithIdResponse createStartWithIdResponse() {
        return new StartWithIdResponse();
    }

    /**
     * Create an instance of {@link ReserveCar }
     * 
     */
    public ReserveCar createReserveCar() {
        return new ReserveCar();
    }

    /**
     * Create an instance of {@link NewCustomerWithIdResponse }
     * 
     */
    public NewCustomerWithIdResponse createNewCustomerWithIdResponse() {
        return new NewCustomerWithIdResponse();
    }

    /**
     * Create an instance of {@link CustomerExistResponse }
     * 
     */
    public CustomerExistResponse createCustomerExistResponse() {
        return new CustomerExistResponse();
    }

    /**
     * Create an instance of {@link CustomerReserveWriteResponse }
     * 
     */
    public CustomerReserveWriteResponse createCustomerReserveWriteResponse() {
        return new CustomerReserveWriteResponse();
    }

    /**
     * Create an instance of {@link NewCustomerWithId }
     * 
     */
    public NewCustomerWithId createNewCustomerWithId() {
        return new NewCustomerWithId();
    }

    /**
     * Create an instance of {@link TransactionAbortedException }
     * 
     */
    public TransactionAbortedException createTransactionAbortedException() {
        return new TransactionAbortedException();
    }

    /**
     * Create an instance of {@link CustomerReserveWrite }
     * 
     */
    public CustomerReserveWrite createCustomerReserveWrite() {
        return new CustomerReserveWrite();
    }

    /**
     * Create an instance of {@link NewCustomer }
     * 
     */
    public NewCustomer createNewCustomer() {
        return new NewCustomer();
    }

    /**
     * Create an instance of {@link AddCarsResponse }
     * 
     */
    public AddCarsResponse createAddCarsResponse() {
        return new AddCarsResponse();
    }

    /**
     * Create an instance of {@link QueryCars }
     * 
     */
    public QueryCars createQueryCars() {
        return new QueryCars();
    }

    /**
     * Create an instance of {@link AddCars }
     * 
     */
    public AddCars createAddCars() {
        return new AddCars();
    }

    /**
     * Create an instance of {@link ReserveFlightResponse }
     * 
     */
    public ReserveFlightResponse createReserveFlightResponse() {
        return new ReserveFlightResponse();
    }

    /**
     * Create an instance of {@link CommitResponse }
     * 
     */
    public CommitResponse createCommitResponse() {
        return new CommitResponse();
    }

    /**
     * Create an instance of {@link ItineraryResponse }
     * 
     */
    public ItineraryResponse createItineraryResponse() {
        return new ItineraryResponse();
    }

    /**
     * Create an instance of {@link QueryFlight }
     * 
     */
    public QueryFlight createQueryFlight() {
        return new QueryFlight();
    }

    /**
     * Create an instance of {@link AddFlightResponse }
     * 
     */
    public AddFlightResponse createAddFlightResponse() {
        return new AddFlightResponse();
    }

    /**
     * Create an instance of {@link InvalidTransactionException }
     * 
     */
    public InvalidTransactionException createInvalidTransactionException() {
        return new InvalidTransactionException();
    }

    /**
     * Create an instance of {@link DeleteCarsResponse }
     * 
     */
    public DeleteCarsResponse createDeleteCarsResponse() {
        return new DeleteCarsResponse();
    }

    /**
     * Create an instance of {@link AddRooms }
     * 
     */
    public AddRooms createAddRooms() {
        return new AddRooms();
    }

    /**
     * Create an instance of {@link DeleteFlightResponse }
     * 
     */
    public DeleteFlightResponse createDeleteFlightResponse() {
        return new DeleteFlightResponse();
    }

    /**
     * Create an instance of {@link QueryCustomerInfoResponse }
     * 
     */
    public QueryCustomerInfoResponse createQueryCustomerInfoResponse() {
        return new QueryCustomerInfoResponse();
    }

    /**
     * Create an instance of {@link NumberFormatException }
     * 
     */
    public NumberFormatException createNumberFormatException() {
        return new NumberFormatException();
    }

    /**
     * Create an instance of {@link QueryRoomsResponse }
     * 
     */
    public QueryRoomsResponse createQueryRoomsResponse() {
        return new QueryRoomsResponse();
    }

    /**
     * Create an instance of {@link Itinerary }
     * 
     */
    public Itinerary createItinerary() {
        return new Itinerary();
    }

    /**
     * Create an instance of {@link Start }
     * 
     */
    public Start createStart() {
        return new Start();
    }

    /**
     * Create an instance of {@link Dump }
     * 
     */
    public Dump createDump() {
        return new Dump();
    }

    /**
     * Create an instance of {@link QueryCarsPriceResponse }
     * 
     */
    public QueryCarsPriceResponse createQueryCarsPriceResponse() {
        return new QueryCarsPriceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCarsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteCarsResponse")
    public JAXBElement<DeleteCarsResponse> createDeleteCarsResponse(DeleteCarsResponse value) {
        return new JAXBElement<DeleteCarsResponse>(_DeleteCarsResponse_QNAME, DeleteCarsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFlightPriceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryFlightPriceResponse")
    public JAXBElement<QueryFlightPriceResponse> createQueryFlightPriceResponse(QueryFlightPriceResponse value) {
        return new JAXBElement<QueryFlightPriceResponse>(_QueryFlightPriceResponse_QNAME, QueryFlightPriceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerReserveWrite }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "customerReserveWrite")
    public JAXBElement<CustomerReserveWrite> createCustomerReserveWrite(CustomerReserveWrite value) {
        return new JAXBElement<CustomerReserveWrite>(_CustomerReserveWrite_QNAME, CustomerReserveWrite.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewCustomerWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "newCustomerWithIdResponse")
    public JAXBElement<NewCustomerWithIdResponse> createNewCustomerWithIdResponse(NewCustomerWithIdResponse value) {
        return new JAXBElement<NewCustomerWithIdResponse>(_NewCustomerWithIdResponse_QNAME, NewCustomerWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealShutdownResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "realShutdownResponse")
    public JAXBElement<RealShutdownResponse> createRealShutdownResponse(RealShutdownResponse value) {
        return new JAXBElement<RealShutdownResponse>(_RealShutdownResponse_QNAME, RealShutdownResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCars }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "addCars")
    public JAXBElement<AddCars> createAddCars(AddCars value) {
        return new JAXBElement<AddCars>(_AddCars_QNAME, AddCars.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRoomsPriceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryRoomsPriceResponse")
    public JAXBElement<QueryRoomsPriceResponse> createQueryRoomsPriceResponse(QueryRoomsPriceResponse value) {
        return new JAXBElement<QueryRoomsPriceResponse>(_QueryRoomsPriceResponse_QNAME, QueryRoomsPriceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItineraryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "itineraryResponse")
    public JAXBElement<ItineraryResponse> createItineraryResponse(ItineraryResponse value) {
        return new JAXBElement<ItineraryResponse>(_ItineraryResponse_QNAME, ItineraryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "removeReservationResponse")
    public JAXBElement<RemoveReservationResponse> createRemoveReservationResponse(RemoveReservationResponse value) {
        return new JAXBElement<RemoveReservationResponse>(_RemoveReservationResponse_QNAME, RemoveReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "startWithId")
    public JAXBElement<StartWithId> createStartWithId(StartWithId value) {
        return new JAXBElement<StartWithId>(_StartWithId_QNAME, StartWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidTransactionException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "InvalidTransactionException")
    public JAXBElement<InvalidTransactionException> createInvalidTransactionException(InvalidTransactionException value) {
        return new JAXBElement<InvalidTransactionException>(_InvalidTransactionException_QNAME, InvalidTransactionException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DumpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "dumpResponse")
    public JAXBElement<DumpResponse> createDumpResponse(DumpResponse value) {
        return new JAXBElement<DumpResponse>(_DumpResponse_QNAME, DumpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCustomerInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryCustomerInfo")
    public JAXBElement<QueryCustomerInfo> createQueryCustomerInfo(QueryCustomerInfo value) {
        return new JAXBElement<QueryCustomerInfo>(_QueryCustomerInfo_QNAME, QueryCustomerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Commit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "commit")
    public JAXBElement<Commit> createCommit(Commit value) {
        return new JAXBElement<Commit>(_Commit_QNAME, Commit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "reserveFlight")
    public JAXBElement<ReserveFlight> createReserveFlight(ReserveFlight value) {
        return new JAXBElement<ReserveFlight>(_ReserveFlight_QNAME, ReserveFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveRoomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "reserveRoomResponse")
    public JAXBElement<ReserveRoomResponse> createReserveRoomResponse(ReserveRoomResponse value) {
        return new JAXBElement<ReserveRoomResponse>(_ReserveRoomResponse_QNAME, ReserveRoomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCustomerInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryCustomerInfoResponse")
    public JAXBElement<QueryCustomerInfoResponse> createQueryCustomerInfoResponse(QueryCustomerInfoResponse value) {
        return new JAXBElement<QueryCustomerInfoResponse>(_QueryCustomerInfoResponse_QNAME, QueryCustomerInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRoomsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryRoomsResponse")
    public JAXBElement<QueryRoomsResponse> createQueryRoomsResponse(QueryRoomsResponse value) {
        return new JAXBElement<QueryRoomsResponse>(_QueryRoomsResponse_QNAME, QueryRoomsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "removeReservation")
    public JAXBElement<RemoveReservation> createRemoveReservation(RemoveReservation value) {
        return new JAXBElement<RemoveReservation>(_RemoveReservation_QNAME, RemoveReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRooms }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryRooms")
    public JAXBElement<QueryRooms> createQueryRooms(QueryRooms value) {
        return new JAXBElement<QueryRooms>(_QueryRooms_QNAME, QueryRooms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerExistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "customerExistResponse")
    public JAXBElement<CustomerExistResponse> createCustomerExistResponse(CustomerExistResponse value) {
        return new JAXBElement<CustomerExistResponse>(_CustomerExistResponse_QNAME, CustomerExistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "newCustomerResponse")
    public JAXBElement<NewCustomerResponse> createNewCustomerResponse(NewCustomerResponse value) {
        return new JAXBElement<NewCustomerResponse>(_NewCustomerResponse_QNAME, NewCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeadlockException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "DeadlockException")
    public JAXBElement<DeadlockException> createDeadlockException(DeadlockException value) {
        return new JAXBElement<DeadlockException>(_DeadlockException_QNAME, DeadlockException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteFlightResponse")
    public JAXBElement<DeleteFlightResponse> createDeleteFlightResponse(DeleteFlightResponse value) {
        return new JAXBElement<DeleteFlightResponse>(_DeleteFlightResponse_QNAME, DeleteFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "addFlightResponse")
    public JAXBElement<AddFlightResponse> createAddFlightResponse(AddFlightResponse value) {
        return new JAXBElement<AddFlightResponse>(_AddFlightResponse_QNAME, AddFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveCarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "reserveCarResponse")
    public JAXBElement<ReserveCarResponse> createReserveCarResponse(ReserveCarResponse value) {
        return new JAXBElement<ReserveCarResponse>(_ReserveCarResponse_QNAME, ReserveCarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveCar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "reserveCar")
    public JAXBElement<ReserveCar> createReserveCar(ReserveCar value) {
        return new JAXBElement<ReserveCar>(_ReserveCar_QNAME, ReserveCar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFlightPrice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryFlightPrice")
    public JAXBElement<QueryFlightPrice> createQueryFlightPrice(QueryFlightPrice value) {
        return new JAXBElement<QueryFlightPrice>(_QueryFlightPrice_QNAME, QueryFlightPrice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryFlightResponse")
    public JAXBElement<QueryFlightResponse> createQueryFlightResponse(QueryFlightResponse value) {
        return new JAXBElement<QueryFlightResponse>(_QueryFlightResponse_QNAME, QueryFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCarsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "addCarsResponse")
    public JAXBElement<AddCarsResponse> createAddCarsResponse(AddCarsResponse value) {
        return new JAXBElement<AddCarsResponse>(_AddCarsResponse_QNAME, AddCarsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "abortResponse")
    public JAXBElement<AbortResponse> createAbortResponse(AbortResponse value) {
        return new JAXBElement<AbortResponse>(_AbortResponse_QNAME, AbortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Start }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "start")
    public JAXBElement<Start> createStart(Start value) {
        return new JAXBElement<Start>(_Start_QNAME, Start.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewCustomerWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "newCustomerWithId")
    public JAXBElement<NewCustomerWithId> createNewCustomerWithId(NewCustomerWithId value) {
        return new JAXBElement<NewCustomerWithId>(_NewCustomerWithId_QNAME, NewCustomerWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCarsPrice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryCarsPrice")
    public JAXBElement<QueryCarsPrice> createQueryCarsPrice(QueryCarsPrice value) {
        return new JAXBElement<QueryCarsPrice>(_QueryCarsPrice_QNAME, QueryCarsPrice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteCustomerResponse")
    public JAXBElement<DeleteCustomerResponse> createDeleteCustomerResponse(DeleteCustomerResponse value) {
        return new JAXBElement<DeleteCustomerResponse>(_DeleteCustomerResponse_QNAME, DeleteCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteCustomer")
    public JAXBElement<DeleteCustomer> createDeleteCustomer(DeleteCustomer value) {
        return new JAXBElement<DeleteCustomer>(_DeleteCustomer_QNAME, DeleteCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRoomsPrice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryRoomsPrice")
    public JAXBElement<QueryRoomsPrice> createQueryRoomsPrice(QueryRoomsPrice value) {
        return new JAXBElement<QueryRoomsPrice>(_QueryRoomsPrice_QNAME, QueryRoomsPrice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Dump }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "dump")
    public JAXBElement<Dump> createDump(Dump value) {
        return new JAXBElement<Dump>(_Dump_QNAME, Dump.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCarsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryCarsResponse")
    public JAXBElement<QueryCarsResponse> createQueryCarsResponse(QueryCarsResponse value) {
        return new JAXBElement<QueryCarsResponse>(_QueryCarsResponse_QNAME, QueryCarsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "startResponse")
    public JAXBElement<StartResponse> createStartResponse(StartResponse value) {
        return new JAXBElement<StartResponse>(_StartResponse_QNAME, StartResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRoomsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "addRoomsResponse")
    public JAXBElement<AddRoomsResponse> createAddRoomsResponse(AddRoomsResponse value) {
        return new JAXBElement<AddRoomsResponse>(_AddRoomsResponse_QNAME, AddRoomsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "startWithIdResponse")
    public JAXBElement<StartWithIdResponse> createStartWithIdResponse(StartWithIdResponse value) {
        return new JAXBElement<StartWithIdResponse>(_StartWithIdResponse_QNAME, StartWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryFlight")
    public JAXBElement<QueryFlight> createQueryFlight(QueryFlight value) {
        return new JAXBElement<QueryFlight>(_QueryFlight_QNAME, QueryFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCarsPriceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryCarsPriceResponse")
    public JAXBElement<QueryCarsPriceResponse> createQueryCarsPriceResponse(QueryCarsPriceResponse value) {
        return new JAXBElement<QueryCarsPriceResponse>(_QueryCarsPriceResponse_QNAME, QueryCarsPriceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveFlightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "reserveFlightResponse")
    public JAXBElement<ReserveFlightResponse> createReserveFlightResponse(ReserveFlightResponse value) {
        return new JAXBElement<ReserveFlightResponse>(_ReserveFlightResponse_QNAME, ReserveFlightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCars }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "queryCars")
    public JAXBElement<QueryCars> createQueryCars(QueryCars value) {
        return new JAXBElement<QueryCars>(_QueryCars_QNAME, QueryCars.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveRoom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "reserveRoom")
    public JAXBElement<ReserveRoom> createReserveRoom(ReserveRoom value) {
        return new JAXBElement<ReserveRoom>(_ReserveRoom_QNAME, ReserveRoom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NumberFormatException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "NumberFormatException")
    public JAXBElement<NumberFormatException> createNumberFormatException(NumberFormatException value) {
        return new JAXBElement<NumberFormatException>(_NumberFormatException_QNAME, NumberFormatException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Itinerary }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "itinerary")
    public JAXBElement<Itinerary> createItinerary(Itinerary value) {
        return new JAXBElement<Itinerary>(_Itinerary_QNAME, Itinerary.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShutdownResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "shutdownResponse")
    public JAXBElement<ShutdownResponse> createShutdownResponse(ShutdownResponse value) {
        return new JAXBElement<ShutdownResponse>(_ShutdownResponse_QNAME, ShutdownResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "addFlight")
    public JAXBElement<AddFlight> createAddFlight(AddFlight value) {
        return new JAXBElement<AddFlight>(_AddFlight_QNAME, AddFlight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Shutdown }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "shutdown")
    public JAXBElement<Shutdown> createShutdown(Shutdown value) {
        return new JAXBElement<Shutdown>(_Shutdown_QNAME, Shutdown.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "customerExist")
    public JAXBElement<CustomerExist> createCustomerExist(CustomerExist value) {
        return new JAXBElement<CustomerExist>(_CustomerExist_QNAME, CustomerExist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerReserveWriteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "customerReserveWriteResponse")
    public JAXBElement<CustomerReserveWriteResponse> createCustomerReserveWriteResponse(CustomerReserveWriteResponse value) {
        return new JAXBElement<CustomerReserveWriteResponse>(_CustomerReserveWriteResponse_QNAME, CustomerReserveWriteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRooms }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "addRooms")
    public JAXBElement<AddRooms> createAddRooms(AddRooms value) {
        return new JAXBElement<AddRooms>(_AddRooms_QNAME, AddRooms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RealShutdown }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "realShutdown")
    public JAXBElement<RealShutdown> createRealShutdown(RealShutdown value) {
        return new JAXBElement<RealShutdown>(_RealShutdown_QNAME, RealShutdown.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "commitResponse")
    public JAXBElement<CommitResponse> createCommitResponse(CommitResponse value) {
        return new JAXBElement<CommitResponse>(_CommitResponse_QNAME, CommitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRoomsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteRoomsResponse")
    public JAXBElement<DeleteRoomsResponse> createDeleteRoomsResponse(DeleteRoomsResponse value) {
        return new JAXBElement<DeleteRoomsResponse>(_DeleteRoomsResponse_QNAME, DeleteRoomsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "newCustomer")
    public JAXBElement<NewCustomer> createNewCustomer(NewCustomer value) {
        return new JAXBElement<NewCustomer>(_NewCustomer_QNAME, NewCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRooms }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteRooms")
    public JAXBElement<DeleteRooms> createDeleteRooms(DeleteRooms value) {
        return new JAXBElement<DeleteRooms>(_DeleteRooms_QNAME, DeleteRooms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Abort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "abort")
    public JAXBElement<Abort> createAbort(Abort value) {
        return new JAXBElement<Abort>(_Abort_QNAME, Abort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionAbortedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "TransactionAbortedException")
    public JAXBElement<TransactionAbortedException> createTransactionAbortedException(TransactionAbortedException value) {
        return new JAXBElement<TransactionAbortedException>(_TransactionAbortedException_QNAME, TransactionAbortedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCars }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteCars")
    public JAXBElement<DeleteCars> createDeleteCars(DeleteCars value) {
        return new JAXBElement<DeleteCars>(_DeleteCars_QNAME, DeleteCars.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFlight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.rm/", name = "deleteFlight")
    public JAXBElement<DeleteFlight> createDeleteFlight(DeleteFlight value) {
        return new JAXBElement<DeleteFlight>(_DeleteFlight_QNAME, DeleteFlight.class, null, value);
    }

}
