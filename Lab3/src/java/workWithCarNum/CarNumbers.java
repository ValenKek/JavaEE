/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workWithCarNum;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import validation.CarNumber;

public class CarNumbers {
    @Min(value = 0)
    @Max(value = 27)
    public static int getRegionNumber(@CarNumber String carNum){
        String regionCode = carNum.substring(0, 2);
        switch(regionCode){
            case "AK": 
            case "KK": return 1;
            case "AB": 
            case "KB": return 2;
            case "AC": 
            case "KC": return 3;
            case "AE": 
            case "KE": return 4;
            case "AH": 
            case "KH": return 5;
            case "AM": 
            case "KM": return 6;
            case "AO": 
            case "KO": return 7;
            case "AP": 
            case "KP": return 8;
            case "AT": 
            case "KT": return 9;
            case "AI": 
            case "KI": return 10;
            case "AA": 
            case "KA": return 11;
            case "BA": 
            case "HA": return 12;
            case "BB": 
            case "HB": return 13;
            case "BC": 
            case "HC": return 14;
            case "BE": 
            case "HE": return 15;
            case "BH": 
            case "HH": return 16;
            case "BI": 
            case "HI": return 17;
            case "BK": 
            case "HK": return 18;
            case "BM": 
            case "HM": return 19;
            case "BO": 
            case "HO": return 20;
            case "AX": 
            case "KX": return 21;
            case "BT": 
            case "HT": return 22;
            case "BX": 
            case "HX": return 23;
            case "CA": 
            case "IA": return 24;
            case "CB": 
            case "IB": return 25;
            case "CE": 
            case "IE": return 26;
            case "CH": 
            case "IH": return 27;
            case "II": return 0;
            default: return -1;
        }
    }
    
    public static String getRegion(@CarNumber String carNum){
        int regNumber = getRegionNumber(carNum);
        switch(regNumber){
            case 0: return "Загальнодержавний номер України";
            case 1: return "Автономна Республіка Крим";
            case 2: return "Віницька область";
            case 3: return "Волинська область";
            case 4: return "Січославська область";
            case 5: return "Донецька область";
            case 6: return "Житомирська область";
            case 7: return "Закарпатська область";
            case 8: return "Запорізька область";
            case 9: return "Івано-Франківська область";
            case 10: return "Київська область";
            case 11: return "місто Київ";
            case 12: return "Кропивницька область";
            case 13: return "Луганська область";
            case 14: return "Львівська область";
            case 15: return "Миколаївська область";
            case 16: return "Одеська область";
            case 17: return "Полтавська область";
            case 18: return "Рівненська область";
            case 19: return "Сумська область";
            case 20: return "Тернопільська область";
            case 21: return "Харківська область";
            case 22: return "Херсонська область";
            case 23: return "Хмельницька область";
            case 24: return "Черкаська область";
            case 25: return "Чернігівська область";
            case 26: return "Чернівецька область";
            case 27: return "місто Севастополь";
            default: return "Введений номер не відповідає жодному регіону України.";
        }
    }
}
