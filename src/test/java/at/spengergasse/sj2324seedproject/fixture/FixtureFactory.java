package at.spengergasse.sj2324seedproject.fixture;

import at.spengergasse.sj2324seedproject.domain.Address;
import at.spengergasse.sj2324seedproject.domain.Customer;
import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.domain.Profile;
import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.domain.Role;
import at.spengergasse.sj2324seedproject.domain.SfpType;
import at.spengergasse.sj2324seedproject.domain.Status;
import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.domain.Type;
import at.spengergasse.sj2324seedproject.domain.User;
import at.spengergasse.sj2324seedproject.foundation.ApiKeyGenerator;
import at.spengergasse.sj2324seedproject.presentation.api.dtos.CustomerDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FixtureFactory {

  private static final ApiKeyGenerator keyGen = new ApiKeyGenerator();

  /// ////////////////////////////////// Producer


  public static Producer producerFixture() {
    return Producer.builder()
        .shortname("MS")
        .name("Micro Soft")
        .build();
  }

  /// ////////////////////////////////// StorageObjectMeta

  public static StorageObjectMeta storageObjectMetaFixture() {
    return StorageObjectMeta.builder()
        .producer(producerFixture())
        .name("meta name1")
        .type(Type.IP_PHONE)
        .osVersion("version1")
        .consumablesPerBox(2)
        .sfpType(SfpType.MM)
        .wavelength("1550nm")
        .interfacespeed("100-Mbps")
        .build();
  }

  public static StorageObjectMeta give_me_a_storageObjectMeta3() {
    return StorageObjectMeta.builder()
        .name("meta name2")
        //                                .type(Type.IP_PHONE)
        .osVersion("version2")
        //                                .consumablesPerBox(2)
        //                                .sfpType(SfpType.MM)
        //                                .wavelength("1310nm")
        //                                .interfacespeed("1000-Mbps")
        .build();
  }

  public static List<Producer> give_me_a_List_of_producer() {
    List<Producer> producersListe = new ArrayList<>();
    producersListe.add(producerFixture());
    return producersListe;
  }

  /// ////////////////////////////////// StorageObject

  public static StorageObject storageObjectFixture() {
    return StorageObject.builder()
        .status(Status.PROJECT)
        .storageObjectMeta(storageObjectMetaFixture())
        .apiKeyID(keyGen.getRandomKey(16))
        .storedStorage(storageFixture())
        .serialNumber("abcd1234")
        .macAddress("ff-ff-ff-ff-ff-ff")
        .remark("this is a remark1")
        .projectDevice(true)
        .storedAtCustomer(customerFixture())
        .build();
  }

  // User - Profile - Reservation - Customer fixtures

  public static User userFixture() {
    return User.builder()
        .userId("U123456789")
        .email("alex@alex.de")
        .password("testpassword")
        .role(Role.ORDERFULLFILLMENT)
        .createdAt(LocalDateTime.now())
        .lastLogin(LocalDateTime.now())
        .isActivated(true)
        .profile(profileFixture())
        .build();
  }


  public static Profile profileFixture() {
    return Profile.builder()
        .username("Alex")
        .firstName("Alex")
        .lastName("Alex")
        .phone("+436991234567")
        .build();
  }

  public static Customer customerFixture() {
    return Customer.builder()
        .connectionNo("1231201310")
        .build();
  }

  public static Reservation reservationFixture(User user) {
    return Reservation.builder()
        .reservedBy(user)
        .reservationId(keyGen.getRandomKey(10))
        .reservationDescription("Testdescription")
        .lastModified(LocalDateTime.now())
        .reservedAt(LocalDateTime.now())
        .reservedFor(customerFixture())
        .completed(false)
        .build();
  }


  public static Storage storageFixture() {
    return Storage.builder()
        .name("Hauptlager DCE4")
        .address(addressFixture())
        .build();

  }

  public static Address addressFixture() {
    return Address.builder()
        .street("Kreuzgasse")
        .number(55)
        .addressAddition("EG")
        .zipcode(1180)
        .city("Wien")
        .build();
  }

  public static Reservation reservationFixture() {
    return Reservation.builder()
        .reservedBy(userFixture())
        .reservationId(keyGen.getRandomKey(10))
        .reservationDescription("Testdescription")
        .lastModified(LocalDateTime.now())
        .reservedAt(LocalDateTime.now())
        .reservedFor(customerFixture())
        .completed(true)
        .build();
  }

  public static CustomerDTO customerDTOFixture(String id) {
    return CustomerDTO.builder()
        .id(id)
        .firstName("John")
        .lastName("Doe")
        .dateOfBirth("01.01.1970")
        .address("Main Street 1")
        .country("USA")
        .city("New York")
        .zipCode("12345")
        .phoneNumber("123456789")
        .email("randomEmail@randomEmail.com")
        .build();
  }
}
