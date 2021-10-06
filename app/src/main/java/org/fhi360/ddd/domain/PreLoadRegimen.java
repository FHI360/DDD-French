package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

import static java.lang.Long.valueOf;

@Entity
@Data
public class PreLoadRegimen implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "regimentype_id")
    private Long regimentypeId;
    @ColumnInfo(name = "regimentype")
    private String regimentype;
//
//    public PreLoadRegimen(Long id, String name, Long regimentypeId, String regimentype) {
//        this.id =  id;
//        this.name = name;
//        this.regimentypeId = regimentypeId;
//        this.regimentype = regimentype;
//    }
//
//
//    public static PreLoadRegimen[] populateRegimen() {
//        return new PreLoadRegimen[]{
//                new PreLoadRegimen((long) 200, "Tenofovir 300mg+ Lamivuldine 300mg+ Dolutegravir 50mg", 300, "ARV"),
//                new PreLoadRegimen((long)82, "Sulphadoxin/Pyrimethamine 500/25mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)83, "Ibuprofen 400mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)84, "Nystatin 500,000iu", 9, "OI Treatment"),
//                new PreLoadRegimen((long)85, "Zithromax 500mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)86, "Metronidazole 400mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)87, "Streptomycin 1g", 9, "OI Treatment"),
//                new PreLoadRegimen((long)88, "Ethambuthol/Isoniazid 400/150mg", 10, "TB Treatment Adult"),
//                new PreLoadRegimen((long)89, "Rifampicin/Isoniazid 150/75mg", 10, "TB Treatment Adult"),
//                new PreLoadRegimen((long)90, "Rifampicin/Isoniazid 60/30mg", 11, "TB Treatment Children"),
//                new PreLoadRegimen((long)91, "Rifampicin/Isoniazid/Pyrazinamide 60/30/150mg	", 11, "TB Treatment Children"),
//                new PreLoadRegimen((long)92, "Rifampicin/Isoniazid/Pyrazinamide/Ethambuthol 150/75/400/275mg", 10, "TB Treatment Adult"),
//                new PreLoadRegimen((long)93, "AZT(300mg)+TDF(300mg)+3TC(150mg)+ATV/r(300mg/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)94, "AZT(300mg)+TDF(300mg)+3TC(150mg)+LPV/r(200mg/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)95, "AZT(300mg)+3TC(150mg)+ATV/r(300mg/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)96, "ABC(60mg)+3TC(30mg)+AZT(60mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen((long)97, "AZT/3TC(300/150mg)+NVP(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen((long)98, "AZT/3TC/NVP(300/150/200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen((long)99, "AZT/3TC(300/150mg)+EFV(600mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen((long)100, "AZT/3TC(300/150mg)+LPV/r(200/50mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen((long)47, "Acyclovir 200mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)48, "Amoxycillin/Clavulanate 625mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)49, "Amoxycillin/Clavulanate 457mg/5ml", 9, "OI Treatment"),
//                new PreLoadRegimen((long)50, "Fluconazole 50mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)51, "Fluconazole 50mg/5ml", 9, "OI Treatment"),
//                new PreLoadRegimen((long)52, "Fluconazole 200mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)53, "Nystatin 100,000iu/ml", 9, "OI Treatment"),
//                new PreLoadRegimen((long)54, "Tinidazole 500mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)55, "Azithromycin 250mg", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)56, "Benzathine Penicillin", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)57, "Ceftriaxone 1g", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)58, "Ciprofloxacin 500mg", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)59, "Doxycycline 100mg", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)60, "Erythromycin 500mg", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)61, "Ofloxacin 200mg", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)101, "AZT/3TC(300/150mg)+EFV(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen((long)102, "3TC/FTC(300/300mg)+ATV/r(300/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)103, "3TC/FTC(300/300mg)+EFV(600mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)104, "3TC/FTC(300/300mg)+LPV/r(200/50mg", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)105, "3TC/FTC(300/300mg)+NVP(200mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)106, "TDF(300mg)+3TC(300mg)+ATV/r(300/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)107, "TDF(300mg)+3TC(300mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)108, "TDF/FTC(300mg/200mg)+ATV/r(300/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)109, "ABC(20mg/ml)+DDI(10mg/ml)+3TC(30mg)", 4, "ART Second Line Children"),
//                new PreLoadRegimen((long)110, "DDI(10mg/ml)+3TC(30mg)+NVP(50mg)", 4, "ART Second Line Children"),
//                new PreLoadRegimen((long)111, "ABC(300mg)+3TC(150mg)+ATV/r(300/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)112, "ABC(300mg)+3TC(150mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)81, "Chlorpheniramine 4mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)62, "Spectinomycine 2g", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen( (long)63, "Tetracycline 500mg", 12, "Other anti-infectives (including STI Medicine)"),
//                new PreLoadRegimen((long)64, "Amitryptiline 25mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)65, "Arthemeter/Lumefantrine 20/120mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)66, "Folic Acid 5mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)67, "Ferrous Sulphate 300mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)68, "Ferrous Gluconate 300mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)69, "Multivitamin", 13, "Other Medicines"),
//                new PreLoadRegimen((long)70, "Multivitamin Syrup", 13, "Other Medicines"),
//                new PreLoadRegimen((long)71, "Ibuprofen 200mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)72, "Loperamide 2mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)73, "Promethezine HCL 25mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)74, "Tramadol HCL 50mg", 13, "Other Medicines"),
//                new PreLoadRegimen((long)75, "Oral Dehydration Solution", 9, "OI Treatment"),
//                new PreLoadRegimen((long)76, "Fluoxetine 20mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)77, "Loratidine 10mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)78, "Metoclopramide 10mg", 9, "OI Treatment"),
//                new PreLoadRegimen((long)79, "Benzoic Acid/Salicylic Acid Ointment 6/3%", 9, "OI Treatment"),
//                new PreLoadRegimen((long)80, "Hydrocortisone 1%", 9, "OI Treatment"),
//                new PreLoadRegimen((long)19, "TDF(300mg)+3TC(150mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)20, "TDF(300mg)+3TC(150mg)+ATV/r(300/100mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen((long)21, "d4T/3TC/NVP(6/30/50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen((long)22, "d4T/3TC/NVP(12/60/100mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen((long)23, "d4T(1mg/ml)+3TC(10mg/ml)+NVP(10mg/ml)", 3, "ART First Line Children"),
//                new PreLoadRegimen(24, "d4T(20mg)+3TC(10mg/ml)+EFV(200mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(25, "AZT(10mg/ml)+3TC(10mg/ml)+NVP(10mg/ml)", 3, "ART First Line Children"),
//                new PreLoadRegimen(26, "AZT(10mg/ml)+3TC(10mg/ml)+EFV(200mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(27, "d4T/3TC/EFV(6/30/200mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(28, "d4T/3TC/EFV(12/60/200mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(29, "AZT/3TC/NVP(60/30/50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(30, "AZT/3TC/EFV(60/30/200mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(31, "ABC(60mg)+3TC(30mg)+NVP(50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(32, "ABC(60mg)+3TC(30mg)+EFV(200mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(33, "ABC(20mg/ml)+DDI(10mg/ml)+LPV/r(80/20mg/ml)", 4, "ART Second Line Children"),
//                new PreLoadRegimen(34, "d4T(6mg)+3TC(30mg)+LPV/r(80/20mg/ml)", 4, "ART Second Line Children"),
//                new PreLoadRegimen(39, "AZT(300mg)+3TC(150mg)", 5, "ARV Prophylaxis for Pregnant Women"),
//                new PreLoadRegimen(40, "AZT(300mg)+3TC(150mg)+EFV(600mg)", 5, "ARV Prophylaxis for Pregnant Women"),
//                new PreLoadRegimen(41, "AZT(300mg)+3TC(150mg)+LPV/r(200/50mg)", 5, "ARV Prophylaxis for Pregnant Women"),
//                new PreLoadRegimen(42, "TDF/FTC(300mg/200mg)+NVP(200mg)", 5, "ARV Prophylaxis for Pregnant Women"),
//                new PreLoadRegimen(43, "TDF/FTC(300mg/200mg)+EFV(600mg)", 5, "ARV Prophylaxis for Pregnant Women"),
//                new PreLoadRegimen(44, "Cotrimoxazole 960mg", 8, "Cotrimoxazole (CTX) Prophylaxis"),
//                new PreLoadRegimen(45, "Cotrimoxazole 480mg", 8, "Cotrimoxazole (CTX) Prophylaxis"),
//                new PreLoadRegimen(46, "Cotrimoxazole 240mg/5ml", 8, "Cotrimoxazole (CTX) Prophylaxis"),
//                new PreLoadRegimen(113, "AZT(300mg)+TDF(300mg)+3TC(150mg)+ATV/r(300mg/100mg)", 14, "Third Line"),
//                new PreLoadRegimen(114, "AZT(300mg)+TDF(300mg)+3TC(150mg)+LPV/r(200mg/50mg)", 14, "Third Line"),
//                new PreLoadRegimen(115, "Isoniazid 300mg", 15, "Isoniazid Preventive Therapy (IPT)"),
//                new PreLoadRegimen(116, "TDF(300mg)+3TC(300mg)+DTG(50mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(117, "TDF(300mg)+FTC(200mg)+DTG(50mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(118, "ABC(20mg/ml)+3TC(300mg)+DTG(50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(119, "ABC(20mg/ml)+FTC(200mg)+DTG(50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(120, "ABC(60mg)+3TC(30mg)+LPV/r(40/10mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(121, "AZT(60mg)+3TC(30mg)+LPV/r(40/10mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(1, "AZT(300mg)+3TC(150mg)+NVP(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(2, "AZT(300mg)+3TC(150mg)+EFV(600mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(3, "AZT(300mg)+3TC(150mg)+ABC(300mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(4, "AZT(300mg)+3TC(150mg)+TDF(300mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(5, "ABC(300mg)+3TC(150mg)+NVP(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(6, "ABC(300mg)+3TC(150mg)+EFV(600mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(7, "d4T(30mg)+3TC(150mg)+NVP(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(8, "d4T(30mg)+3TC(150mg)+EFV(600mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(9, "TDF(300mg)+3TC(300mg)+NVP(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(10, "TDF(300mg)+3TC(300mg)+EFV(600mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(11, "TDF/FTC(300/200mg)+NVP(200mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(12, "TDF/FTC(300/200mg)+EFV(600mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(13, "AZT(300mg)+3TC(150mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen(14, "AZT(300mg)+TDF/FTC(300/200mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen(15, "ABC(300mg)+DDI(400mg)+IDV/r(400/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen(16, "ABC(300mg)+DDI(400mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen(35, "d4T(12mg)+3TC(60mg)+LPV/r(80/20mg/ml)", 4, "ART Second Line Children"),
//                new PreLoadRegimen(36, "AZT(60mg)+3TC(30mg)+LPV/r(80/20mg/ml)", 4, "ART Second Line Children"),
//                new PreLoadRegimen(37, "ABC(60mg)+3TC(30mg)+LPV/r(80/20mg/ml)", 4, "ART Second Line Children"),
//                new PreLoadRegimen(38, "AZT(300mg)", 5, "ARV Prophylaxis for Pregnant Women"),
//                new PreLoadRegimen(17, "d4T(30mg)+3TC(150mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen(18, "TDF/FTC(300/200mg)+LPV/r(200/50mg)", 2, "ART Second Line Adult"),
//                new PreLoadRegimen(122, "TDF(300mg)+3TC(30mg)+DTG(50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(123, "TDF(300mg)+3TC(300mg)+DTG(50mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(124, "ABC(60mg)+3TC(30mg)+DTG(50mg)", 3, "ART First Line Children"),
//                new PreLoadRegimen(125, "ABC(600mg)+3TC(300mg)+DTG(50mg)", 1, "ART First Line Adult"),
//                new PreLoadRegimen(126, "ABC(600mg)+3TC(300mg)+LPV/r(200/50mg)+DTG(50mg)", 14, "Third Line"),
//                new PreLoadRegimen(127, "TDF(300mg)+3TC(300mg)+LPV/r(200/50mg)+DTG(50mg)", 14, "Third Line"),
//                new PreLoadRegimen(128, "DTG(50mg)+LPV/r(200/50mg)", 14, "Third Line")
//
//
//        };
//    }
}
