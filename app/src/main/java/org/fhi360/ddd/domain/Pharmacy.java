package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@Entity
public class Pharmacy implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "facility_id")
    private Long facilityId;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "pin")
    private String pin;
    @ColumnInfo(name = "date_registration")
    private String dateRegistration;
    @ColumnInfo(name = "username")
    private String username;
//
////    public Pharmacy() {
////    }
////
//    public Pharmacy(Long id, String name, Long facilityId, String address, String phone, String email, String type, String pinCode) {
//        this.id = id;
//        this.name = name;
//        this.facilityId = facilityId;
//        this.address = address;
//        this.phone = phone;
//        this.email = email;
//        this.type = type;
//        this.pinCode = pinCode;
//    }
//
//
//    public static Pharmacy[] populatePharmacy() {
//        return new Pharmacy[]{
//                new Pharmacy(10L, "Christa Pharmacy", (long)1, "Banjor, Hotel Africa", "776550094", "spdarciba@gmail.com", "Pharmacy", "LI0001"),
//                new Pharmacy(20L, "Loyalty Pharmacy", (long)1, "St. paul Bridge after Island clinic Junction", "", "amestfarley121991@gmail.com", "Pharmacy", "LI0002"),
//
//                new Pharmacy(30L, "Redemption Pharmacy", (long)1, "Tweh Farm", "778561255", "", "Pharmacy", "LI0003"),
//
//                new Pharmacy(40L, "Kingdom Pharmacy", (long)1, "Caldwell Junction opposite IB", "881323908", "Kingdompharmaceuticalsliberia@gmail.co", "Pharmacy", "LI0004"),
//
//                new Pharmacy(50L, "God's Divine Pharmacy", (long)1, "Upper Caldwell before the Police station", "778353157", "nathanielnabieu@gmail.comm", "Pharmacy", "LI0005"),
//
//                new Pharmacy(60L, "Marvelous Pharmacy", (long)1, "Upper Caldwell before the Police station", "778383717", "creationmarvelous87@gmail.com", "Pharmacy", "LI0006"),
//
//                new Pharmacy(70L, "Success B. C Pharmacy", (long)1, "Clara Town Gas Station with in the Auto pack ", "778829217", "anomweenwabuoke@gmail.com", "Pharmacy", "LI0007"),
//
//                new Pharmacy(80L, "God's Lead Pharmacy", (long)1, "Clara Town  Store using the store road", "770638266", "", "Pharmacy", "LI0008"),
//
//                new Pharmacy(90L, "Sia Kripa Pharmacy", (long)1, "New Georgia Junction adjecent Green Flower Intertainment", "775198756", "UNIQUEPARASRAM.KPM@GMAIL.COM", "Pharmacy", "LI0009"),
//                new Pharmacy(100L, "Delight Pharmacy", (long)1, "JJY Gardnersville  Adjecent the LBDI Bank", "776974327", "dwahne645@gmail.com", "Pharmacy", "LI0010"),
//                new Pharmacy(110L, "United Waum Pharmacy", (long)1, "Stephen Tolbert Estate Junction", "886947209", "powoeelimelechi@gmail.com", "Pharmacy", "LI0011"),
//
//                new Pharmacy(120L, "Van Pharmacy", (long)1, "Barnersville Kebah Township opposite the Barnersville Health Center", "770162881", "spdarciba@gmail.com", "Pharmacy", "LI0012"),
//
//                new Pharmacy(130L, "Mayoubah & Sons Pharmacy", (long)1, "Gardnersville Town Hall Junction", "777220074", "layeea.donzo@gmail.com", "Pharmacy", "LI0013")
//
//        };
//
//
//    }

}
