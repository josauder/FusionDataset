package de.kdld16.hpi.util;
import de.kdld16.hpi.resolver.*;

import java.util.HashMap;

/**
 * Created by jonathan on 11/14/16.
 */
public class ClassifyProperties {
    //TODO: Everything, just a dummy so far



    public static HashMap<String,Class<? extends Resolver>> acceptOnlyOne;
    static {
        acceptOnlyOne= new HashMap<>();
    //    acceptOnlyOne.put("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",ModeResolver.class);
    //    acceptOnlyOne.put("<http://dbpedia.org/ontology/weight>", SimpleFloatMeanResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/acceleration>", FloatModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/populationTotal>",FloatModeResolver.class);
    /*    acceptOnlyOne.put("<http://dbpedia.org/ontology/wheelbase>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/co2Emission>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/retirementDate>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/averageAnnualGeneration>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/height>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/topSpeed>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/birthYear>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/restingDate>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/zipCode>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/deathDate>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/fuelCapacity>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/latestReleaseDate>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/netIncome>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/deathYear>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/birthDate>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/installedCapacity>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/foalDate>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/redline>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/diameter>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/length>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/operatingIncome>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/torqueOutput>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/width>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/marketCapitalisation>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/fuelConsumption>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/displacement>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/powerOutput>", ModeResolver.class);
        acceptOnlyOne.put("<http://www.w3.org/2003/01/geo/wgs84_pos#lat>", ModeResolver.class);
        acceptOnlyOne.put("<http://www.w3.org/2003/01/geo/wgs84_pos#long>", ModeResolver.class);
        acceptOnlyOne.put("<http://www.georss.org/georss/point>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/iso31661Code>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/iso6391Code>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/iso6392Code>", ModeResolver.class);
        acceptOnlyOne.put("<http://dbpedia.org/ontology/iso6393Code>", ModeResolver.class);*/
        acceptOnlyOne.put("<http://dbpedia.org/ontology/totalPopulation>", FloatModeResolver.class);
        acceptOnlyOne.put("<http://xmlns.com/foaf/0.1/homepage", ModeResolver.class);
    }

    /*
    static String[] integerTypeArray = {
            "<http://www.w3.org/2001/XMLSchema#integer>",
            "<http://www.w3.org/2001/XMLSchema#positiveInteger>",
            "<http://www.w3.org/2001/XMLSchema#nonNegativeInteger>",
            "<http://www.w3.org/2001/XMLSchema#nonPositiveInteger>",
            "<http://www.w3.org/2001/XMLSchema#negativeInteger>",
    };
    public static HashSet<String> integerType = new HashSet<>(Arrays.asList(integerTypeArray));

    static String[] floatTypeArray = {
            "<http://www.w3.org/2001/XMLSchema#double>",
            "<http://www.w3.org/2001/XMLSchema#float>",
            "<http://dbpedia.org/datatype/squareMetre>",
            "<http://dbpedia.org/datatype/squareMillimetre>",
            "<http://dbpedia.org/datatype/squareCentimetre>",
            "<http://dbpedia.org/datatype/squareDecimetre>",
            "<http://dbpedia.org/datatype/squareDecametre>",
            "<http://dbpedia.org/datatype/squareHectometre>",
            "<http://dbpedia.org/datatype/squareKilometre>",
            "<http://dbpedia.org/datatype/hectare>",
            "<http://dbpedia.org/datatype/squareInch>",
            "<http://dbpedia.org/datatype/squareFoot>",
            "<http://dbpedia.org/datatype/squareYard>",
            "<http://dbpedia.org/datatype/acre>",
            "<http://dbpedia.org/datatype/squareMile>",
            "<http://dbpedia.org/datatype/squareNauticalMile>",
            "<http://dbpedia.org/datatype/usDollar>",
            "<http://dbpedia.org/datatype/euro>",
            "<http://dbpedia.org/datatype/poundSterling>",
            "<http://dbpedia.org/datatype/japaneseYen>",
            "<http://dbpedia.org/datatype/russianRouble>",
            "<http://dbpedia.org/datatype/unitedArabEmiratesDirham>",
            "<http://dbpedia.org/datatype/afghanAfghani>",
            "<http://dbpedia.org/datatype/albanianLek>",
            "<http://dbpedia.org/datatype/armenianDram>",
            "<http://dbpedia.org/datatype/netherlandsAntilleanGuilder>",
            "<http://dbpedia.org/datatype/angolanKwanza>",
            "<http://dbpedia.org/datatype/argentinePeso>",
            "<http://dbpedia.org/datatype/australianDollar>",
            "<http://dbpedia.org/datatype/arubanGuilder>",
            "<http://dbpedia.org/datatype/bosniaAndHerzegovinaConvertibleMarks>",
            "<http://dbpedia.org/datatype/barbadosDollar>",
            "<http://dbpedia.org/datatype/bangladeshiTaka>",
            "<http://dbpedia.org/datatype/bulgarianLev>",
            "<http://dbpedia.org/datatype/bahrainiDinar>",
            "<http://dbpedia.org/datatype/burundianFranc>",
            "<http://dbpedia.org/datatype/bermudianDollar>",
            "<http://dbpedia.org/datatype/bruneiDollar>",
            "<http://dbpedia.org/datatype/bolivianBoliviano>",
            "<http://dbpedia.org/datatype/brazilianReal>",
            "<http://dbpedia.org/datatype/bahamianDollar>",
            "<http://dbpedia.org/datatype/bhutaneseNgultrum>",
            "<http://dbpedia.org/datatype/botswanaPula>",
            "<http://dbpedia.org/datatype/belarussianRuble>",
            "<http://dbpedia.org/datatype/belizeDollar>",
            "<http://dbpedia.org/datatype/canadianDollar>",
            "<http://dbpedia.org/datatype/congoleseFranc>",
            "<http://dbpedia.org/datatype/swissFranc>",
            "<http://dbpedia.org/datatype/chileanPeso>",
            "<http://dbpedia.org/datatype/renminbi>",
            "<http://dbpedia.org/datatype/colombianPeso>",
            "<http://dbpedia.org/datatype/unidadDeValorReal>",
            "<http://dbpedia.org/datatype/costaRicanColon>",
            "<http://dbpedia.org/datatype/cubanPeso>",
            "<http://dbpedia.org/datatype/capeVerdeEscudo>",
            "<http://dbpedia.org/datatype/czechKoruna>",
            "<http://dbpedia.org/datatype/djiboutianFranc>",
            "<http://dbpedia.org/datatype/danishKrone>",
            "<http://dbpedia.org/datatype/dominicanPeso>",
            "<http://dbpedia.org/datatype/algerianDinar>",
            "<http://dbpedia.org/datatype/estonianKroon>",
            "<http://dbpedia.org/datatype/egyptianPound>",
            "<http://dbpedia.org/datatype/eritreanNakfa>",
            "<http://dbpedia.org/datatype/ethiopianBirr>",
            "<http://dbpedia.org/datatype/fijiDollar>",
            "<http://dbpedia.org/datatype/falklandIslandsPound>",
            "<http://dbpedia.org/datatype/georgianLari>",
            "<http://dbpedia.org/datatype/ghanaianCedi>",
            "<http://dbpedia.org/datatype/gibraltarPound>",
            "<http://dbpedia.org/datatype/gambianDalasi>",
            "<http://dbpedia.org/datatype/guineaFranc>",
            "<http://dbpedia.org/datatype/guatemalanQuetzal>",
            "<http://dbpedia.org/datatype/guyanaDollar>",
            "<http://dbpedia.org/datatype/hongKongDollar>",
            "<http://dbpedia.org/datatype/honduranLempira>",
            "<http://dbpedia.org/datatype/croatianKuna>",
            "<http://dbpedia.org/datatype/haitiGourde>",
            "<http://dbpedia.org/datatype/hungarianForint>",
            "<http://dbpedia.org/datatype/indonesianRupiah>",
            "<http://dbpedia.org/datatype/israeliNewSheqel>",
            "<http://dbpedia.org/datatype/indianRupee>",
            "<http://dbpedia.org/datatype/iraqiDinar>",
            "<http://dbpedia.org/datatype/iranianRial>",
            "<http://dbpedia.org/datatype/icelandKrona>",
            "<http://dbpedia.org/datatype/jamaicanDollar>",
            "<http://dbpedia.org/datatype/jordanianDinar>",
            "<http://dbpedia.org/datatype/kenyanShilling>",
            "<http://dbpedia.org/datatype/kyrgyzstaniSom>",
            "<http://dbpedia.org/datatype/uzbekistanSom>",
            "<http://dbpedia.org/datatype/cambodianRiel>",
            "<http://dbpedia.org/datatype/comorianFranc>",
            "<http://dbpedia.org/datatype/northKoreanWon>",
            "<http://dbpedia.org/datatype/southKoreanWon>",
            "<http://dbpedia.org/datatype/kuwaitiDinar>",
            "<http://dbpedia.org/datatype/caymanIslandsDollar>",
            "<http://dbpedia.org/datatype/kazakhstaniTenge>",
            "<http://dbpedia.org/datatype/laoKip>",
            "<http://dbpedia.org/datatype/lebanesePound>",
            "<http://dbpedia.org/datatype/sriLankanRupee>",
            "<http://dbpedia.org/datatype/liberianDollar>",
            "<http://dbpedia.org/datatype/lesothoLoti>",
            "<http://dbpedia.org/datatype/lithuanianLitas>",
            "<http://dbpedia.org/datatype/latvianLats>",
            "<http://dbpedia.org/datatype/libyanDinar>",
            "<http://dbpedia.org/datatype/moroccanDirham>",
            "<http://dbpedia.org/datatype/moldovanLeu>",
            "<http://dbpedia.org/datatype/malagasyAriary>",
            "<http://dbpedia.org/datatype/macedonianDenar>",
            "<http://dbpedia.org/datatype/myanmaKyat>",
            "<http://dbpedia.org/datatype/mongolianTögrög>",
            "<http://dbpedia.org/datatype/macanesePataca>",
            "<http://dbpedia.org/datatype/mauritanianOuguiya>",
            "<http://dbpedia.org/datatype/mauritianRupee>",
            "<http://dbpedia.org/datatype/maldivianRufiyaa>",
            "<http://dbpedia.org/datatype/malawianKwacha>",
            "<http://dbpedia.org/datatype/zambianKwacha>",
            "<http://dbpedia.org/datatype/mexicanPeso>",
            "<http://dbpedia.org/datatype/malaysianRinggit>",
            "<http://dbpedia.org/datatype/mozambicanMetical>",
            "<http://dbpedia.org/datatype/namibianDollar>",
            "<http://dbpedia.org/datatype/nigerianNaira>",
            "<http://dbpedia.org/datatype/nicaraguanCórdoba>",
            "<http://dbpedia.org/datatype/norwegianKrone>",
            "<http://dbpedia.org/datatype/nepaleseRupee>",
            "<http://dbpedia.org/datatype/newZealandDollar>",
            "<http://dbpedia.org/datatype/omaniRial>",
            "<http://dbpedia.org/datatype/panamanianBalboa>",
            "<http://dbpedia.org/datatype/peruvianNuevoSol>",
            "<http://dbpedia.org/datatype/papuaNewGuineanKina>",
            "<http://dbpedia.org/datatype/philippinePeso>",
            "<http://dbpedia.org/datatype/pakistaniRupee>",
            "<http://dbpedia.org/datatype/polishZłoty>",
            "<http://dbpedia.org/datatype/paraguayanGuarani>",
            "<http://dbpedia.org/datatype/qatariRial>",
            "<http://dbpedia.org/datatype/romanianNewLeu>",
            "<http://dbpedia.org/datatype/serbianDinar>",
            "<http://dbpedia.org/datatype/rwandaFranc>",
            "<http://dbpedia.org/datatype/saudiRiyal>",
            "<http://dbpedia.org/datatype/solomonIslandsDollar>",
            "<http://dbpedia.org/datatype/seychellesRupee>",
            "<http://dbpedia.org/datatype/sudanesePound>",
            "<http://dbpedia.org/datatype/swedishKrona>",
            "<http://dbpedia.org/datatype/singaporeDollar>",
            "<http://dbpedia.org/datatype/saintHelenaPound>",
            "<http://dbpedia.org/datatype/slovakKoruna>",
            "<http://dbpedia.org/datatype/sierraLeoneanLeone>",
            "<http://dbpedia.org/datatype/somaliShilling>",
            "<http://dbpedia.org/datatype/surinamDollar>",
            "<http://dbpedia.org/datatype/sãoToméAndPríncipeDobra>",
            "<http://dbpedia.org/datatype/syrianPound>",
            "<http://dbpedia.org/datatype/swaziLilangeni>",
            "<http://dbpedia.org/datatype/thaiBaht>",
            "<http://dbpedia.org/datatype/tajikistaniSomoni>",
            "<http://dbpedia.org/datatype/turkmenistaniManat>",
            "<http://dbpedia.org/datatype/azerbaijaniManat>",
            "<http://dbpedia.org/datatype/tunisianDinar>",
            "<http://dbpedia.org/datatype/tonganPaanga>",
            "<http://dbpedia.org/datatype/turkishLira>",
            "<http://dbpedia.org/datatype/trinidadAndTobagoDollar>",
            "<http://dbpedia.org/datatype/newTaiwanDollar>",
            "<http://dbpedia.org/datatype/tanzanianShilling>",
            "<http://dbpedia.org/datatype/ukrainianHryvnia>",
            "<http://dbpedia.org/datatype/ugandaShilling>",
            "<http://dbpedia.org/datatype/uruguayanPeso>",
            "<http://dbpedia.org/datatype/venezuelanBolívar>",
            "<http://dbpedia.org/datatype/vanuatuVatu>",
            "<http://dbpedia.org/datatype/samoanTala>",
            "<http://dbpedia.org/datatype/centralAfricanCfaFranc>",
            "<http://dbpedia.org/datatype/eastCaribbeanDollar>",
            "<http://dbpedia.org/datatype/westAfricanCfaFranc>",
            "<http://dbpedia.org/datatype/cfpFranc>",
            "<http://dbpedia.org/datatype/yemeniRial>",
            "<http://dbpedia.org/datatype/southAfricanRand>",
            "<http://dbpedia.org/datatype/zimbabweanDollar>",
            "<http://dbpedia.org/datatype/kilogramPerCubicMetre>",
            "<http://dbpedia.org/datatype/kilogramPerLitre>",
            "<http://dbpedia.org/datatype/gramPerCubicCentimetre>",
            "<http://dbpedia.org/datatype/gramPerMillilitre>",
            "<http://dbpedia.org/datatype/joule>",
            "<http://dbpedia.org/datatype/kilojoule>",
            "<http://dbpedia.org/datatype/erg>",
            "<http://dbpedia.org/datatype/milliwattHour>",
            "<http://dbpedia.org/datatype/wattHour>",
            "<http://dbpedia.org/datatype/kilowattHour>",
            "<http://dbpedia.org/datatype/megawattHour>",
            "<http://dbpedia.org/datatype/gigawattHour>",
            "<http://dbpedia.org/datatype/terawattHour>",
            "<http://dbpedia.org/datatype/electronVolt>",
            "<http://dbpedia.org/datatype/millicalorie>",
            "<http://dbpedia.org/datatype/calorie>",
            "<http://dbpedia.org/datatype/kilocalorie>",
            "<http://dbpedia.org/datatype/megacalorie>",
            "<http://dbpedia.org/datatype/inchPound>",
            "<http://dbpedia.org/datatype/footPound>",
            "<http://dbpedia.org/datatype/cubicMetrePerSecond>",
            "<http://dbpedia.org/datatype/cubicFeetPerSecond>",
            "<http://dbpedia.org/datatype/cubicMetrePerYear>",
            "<http://dbpedia.org/datatype/cubicFeetPerYear>",
            "<http://dbpedia.org/datatype/newton>",
            "<http://dbpedia.org/datatype/nanonewton>",
            "<http://dbpedia.org/datatype/millinewton>",
            "<http://dbpedia.org/datatype/kilonewton>",
            "<http://dbpedia.org/datatype/meganewton>",
            "<http://dbpedia.org/datatype/giganewton>",
            "<http://dbpedia.org/datatype/tonneForce>",
            "<http://dbpedia.org/datatype/megapond>",
            "<http://dbpedia.org/datatype/kilogramForce>",
            "<http://dbpedia.org/datatype/kilopond>",
            "<http://dbpedia.org/datatype/gramForce>",
            "<http://dbpedia.org/datatype/pond>",
            "<http://dbpedia.org/datatype/milligramForce>",
            "<http://dbpedia.org/datatype/millipond>",
            "<http://dbpedia.org/datatype/poundal>",
            "<http://dbpedia.org/datatype/hertz>",
            "<http://dbpedia.org/datatype/millihertz>",
            "<http://dbpedia.org/datatype/kilohertz>",
            "<http://dbpedia.org/datatype/megahertz>",
            "<http://dbpedia.org/datatype/gigahertz>",
            "<http://dbpedia.org/datatype/terahertz>",
            "<http://dbpedia.org/datatype/kilometresPerLitre>",
            "<http://dbpedia.org/datatype/litresPerKilometre>",
            "<http://dbpedia.org/datatype/milesPerImperialGallon>",
            "<http://dbpedia.org/datatype/milesPerUsGallon>",
            "<http://dbpedia.org/datatype/imperialGallonsPerMile>",
            "<http://dbpedia.org/datatype/usGallonsPerMile>",
            "<http://dbpedia.org/datatype/byte>",
            "<http://dbpedia.org/datatype/bit>",
            "<http://dbpedia.org/datatype/kilobit>",
            "<http://dbpedia.org/datatype/megabit>",
            "<http://dbpedia.org/datatype/kilobyte>",
            "<http://dbpedia.org/datatype/megabyte>",
            "<http://dbpedia.org/datatype/gigabyte>",
            "<http://dbpedia.org/datatype/terabyte>",
            "<http://dbpedia.org/datatype/metre>",
            "<http://dbpedia.org/datatype/nanometre>",
            "<http://dbpedia.org/datatype/micrometre>",
            "<http://dbpedia.org/datatype/millimetre>",
            "<http://dbpedia.org/datatype/centimetre>",
            "<http://dbpedia.org/datatype/decimetre>",
            "<http://dbpedia.org/datatype/decametre>",
            "<http://dbpedia.org/datatype/hectometre>",
            "<http://dbpedia.org/datatype/kilometre>",
            "<http://dbpedia.org/datatype/megametre>",
            "<http://dbpedia.org/datatype/gigametre>",
            "<http://dbpedia.org/datatype/inch>",
            "<http://dbpedia.org/datatype/hand>",
            "<http://dbpedia.org/datatype/foot>",
            "<http://dbpedia.org/datatype/yard>",
            "<http://dbpedia.org/datatype/fathom>",
            "<http://dbpedia.org/datatype/rod>",
            "<http://dbpedia.org/datatype/chain>",
            "<http://dbpedia.org/datatype/furlong>",
            "<http://dbpedia.org/datatype/mile>",
            "<http://dbpedia.org/datatype/nautialMile>",
            "<http://dbpedia.org/datatype/astronomicalUnit>",
            "<http://dbpedia.org/datatype/lightYear>",
            "<http://dbpedia.org/datatype/kilolightYear>",
            "<http://dbpedia.org/datatype/gram>",
            "<http://dbpedia.org/datatype/milligram>",
            "<http://dbpedia.org/datatype/kilogram>",
            "<http://dbpedia.org/datatype/tonne>",
            "<http://dbpedia.org/datatype/stone>",
            "<http://dbpedia.org/datatype/pound>",
            "<http://dbpedia.org/datatype/ounce>",
            "<http://dbpedia.org/datatype/grain>",
            "<http://dbpedia.org/datatype/carat>",
            "<http://dbpedia.org/datatype/atomicMassUnit>",
            "<http://dbpedia.org/datatype/inhabitantsPerSquareKilometre>",
            "<http://dbpedia.org/datatype/inhabitantsPerHectare>",
            "<http://dbpedia.org/datatype/inhabitantsPerSquareMile>",
            "<http://dbpedia.org/datatype/inhabitantsPerAcre>",
            "<http://dbpedia.org/datatype/watt>",
            "<http://dbpedia.org/datatype/kilowatt>",
            "<http://dbpedia.org/datatype/milliwatt>",
            "<http://dbpedia.org/datatype/megawatt>",
            "<http://dbpedia.org/datatype/gigawatt>",
            "<http://dbpedia.org/datatype/horsepower>",
            "<http://dbpedia.org/datatype/pferdestaerke>",
            "<http://dbpedia.org/datatype/brakeHorsepower>",
            "<http://dbpedia.org/datatype/ampere>",
            "<http://dbpedia.org/datatype/kiloampere>",
            "<http://dbpedia.org/datatype/milliampere>",
            "<http://dbpedia.org/datatype/microampere>",
            "<http://dbpedia.org/datatype/volt>",
            "<http://dbpedia.org/datatype/megavolt>",
            "<http://dbpedia.org/datatype/kilovolt>",
            "<http://dbpedia.org/datatype/millivolt>",
            "<http://dbpedia.org/datatype/microvolt>",
            "<http://dbpedia.org/datatype/pascal>",
            "<http://dbpedia.org/datatype/millipascal>",
            "<http://dbpedia.org/datatype/hectopascal>",
            "<http://dbpedia.org/datatype/kilopascal>",
            "<http://dbpedia.org/datatype/megapascal>",
            "<http://dbpedia.org/datatype/millibar>",
            "<http://dbpedia.org/datatype/decibar>",
            "<http://dbpedia.org/datatype/bar>",
            "<http://dbpedia.org/datatype/standardAtmosphere>",
            "<http://dbpedia.org/datatype/poundPerSquareInch>",
            "<http://dbpedia.org/datatype/kilometrePerHour>",
            "<http://dbpedia.org/datatype/metrePerSecond>",
            "<http://dbpedia.org/datatype/kilometrePerSecond>",
            "<http://dbpedia.org/datatype/milePerHour>",
            "<http://dbpedia.org/datatype/footPerSecond>",
            "<http://dbpedia.org/datatype/footPerMinute>",
            "<http://dbpedia.org/datatype/knot>",
            "<http://dbpedia.org/datatype/kelvin>",
            "<http://dbpedia.org/datatype/degreeCelsius>",
            "<http://dbpedia.org/datatype/degreeFahrenheit>",
            "<http://dbpedia.org/datatype/degreeRankine>",
            "<http://dbpedia.org/datatype/second>",
            "<http://dbpedia.org/datatype/millisecond>",
            "<http://dbpedia.org/datatype/microsecond>",
            "<http://dbpedia.org/datatype/nanosecond>",
            "<http://dbpedia.org/datatype/minute>",
            "<http://dbpedia.org/datatype/hour>",
            "<http://dbpedia.org/datatype/day>",
            "<http://dbpedia.org/datatype/newtonMetre>",
            "<http://dbpedia.org/datatype/newtonMillimetre>",
            "<http://dbpedia.org/datatype/newtonCentimetre>",
            "<http://dbpedia.org/datatype/poundFoot>",
            "<http://dbpedia.org/datatype/cubicMetre>",
            "<http://dbpedia.org/datatype/cubicMillimetre>",
            "<http://dbpedia.org/datatype/cubicCentimetre>",
            "<http://dbpedia.org/datatype/cubicDecimetre>",
            "<http://dbpedia.org/datatype/cubicDecametre>",
            "<http://dbpedia.org/datatype/cubicHectometre>",
            "<http://dbpedia.org/datatype/cubicKilometre>",
            "<http://dbpedia.org/datatype/microlitre>",
            "<http://dbpedia.org/datatype/millilitre>",
            "<http://dbpedia.org/datatype/centilitre>",
            "<http://dbpedia.org/datatype/decilitre>",
            "<http://dbpedia.org/datatype/litre>",
            "<http://dbpedia.org/datatype/decalitre>",
            "<http://dbpedia.org/datatype/hectolitre>",
            "<http://dbpedia.org/datatype/kilolitre>",
            "<http://dbpedia.org/datatype/megalitre>",
            "<http://dbpedia.org/datatype/gigalitre>",
            "<http://dbpedia.org/datatype/cubicMile>",
            "<http://dbpedia.org/datatype/cubicYard>",
            "<http://dbpedia.org/datatype/cubicFoot>",
            "<http://dbpedia.org/datatype/cubicInch>",
            "<http://dbpedia.org/datatype/imperialBarrel>",
            "<http://dbpedia.org/datatype/usBarrel>",
            "<http://dbpedia.org/datatype/imperialBarrelOil>",
            "<http://dbpedia.org/datatype/usBarrelOil>",
            "<http://dbpedia.org/datatype/imperialGallon>",
            "<http://dbpedia.org/datatype/usGallon>",
            "<http://dbpedia.org/datatype/gramPerKilometre>",
            "<http://dbpedia.org/datatype/perCent>",
            "<http://dbpedia.org/datatype/perMil>"
    };
    public static HashSet<String> floatType = new HashSet<>(Arrays.asList(floatTypeArray));

    static String[] dateTypeArray = {
        "<http://www.w3.org/2001/XMLSchema#date>",
        "<http://www.w3.org/2001/XMLSchema#time>",
        "<http://www.w3.org/2001/XMLSchema#dateTime>",
        "<http://www.w3.org/2001/XMLSchema#gYear>",
        "<http://www.w3.org/2001/XMLSchema#gYearMonth>",
        "<http://www.w3.org/2001/XMLSchema#gMonth>",
        "<http://www.w3.org/2001/XMLSchema#gMonthDay>",
        "<http://www.w3.org/2001/XMLSchema#gDay>"
    };
    public static HashSet<String> dateType = new HashSet<>(Arrays.asList(dateTypeArray));


    public static HashMap<String,String> ranges;
    public static HashMap<String,Resolver> resolverHashMap;
    static {
        Properties properties = new Properties();
        ranges = new HashMap<>();
        resolverHashMap= new HashMap<>();
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
            BufferedReader br = new BufferedReader(new FileReader(properties.getProperty("ontologyRangesOnlyFile")));
            String line;
            while ((line=br.readLine())!=null) {
                String[] triple= line.split(" ",3);
                ranges.put(triple[0],triple[1]);
            }



            for (String property : acceptOnlyOneArray) {

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
