package cookbook.model;

public class MeasurementConverter {

    public float convert(float value, String inMsr, String toMsr){
        float ret = 0;

        switch(inMsr){
            case "kg":
                ret = KGtoG(value);
                break;
            case "g":
                ret = GtoG(value);
                break;
            case "l":
                ret = LtoG(value);
                break;
            case "ml":
                ret = MLtoG(value);
                break;
            case "tbsp":
                ret = TBSPtoG(value);
                break;
            case "tsp":
                ret = TSPtoG(value);
                break;
            case "cup":
                ret = CUPtoG(value);
                break;
            default:
                System.out.println("Error: This type cannot be converted.");
                return -1;
        }

        //ret value is now in grams

        switch(toMsr) {
            case "kg":
                ret = GtoKG(ret);
                break;
            case "g":
                ret = GtoG(ret);
                break;
            case "l":
                ret = GtoL(ret);
                break;
            case "ml":
                ret = GtoML(ret);
                break;
            case "tbsp":
                ret = GtoTBSP(ret);
                break;
            case "tsp":
                ret = GtoTSP(ret);
                break;
            case "cup":
                ret = GtoCUP(ret);
                break;
            default:
                System.out.println("Error: This type cannot be converted.");
                return -1;
        }

        //ret is now converted

        return ret;
    }

    private float KGtoG(float qty){
        return qty * 1000;
    }

    private float GtoG(float qty){
        return qty;
    }

    private float LtoG(float qty){
        return qty * 1000;
    }

    private float MLtoG(float qty){
        return qty;
    }

    private float TBSPtoG(float qty){
        return qty * 15;
    }

    private float TSPtoG(float qty){
        return qty * 5.7f;
    }

    private float CUPtoG(float qty){
        return qty * 340;
    }

    private float GtoKG(float qty){
        return qty / 1000;
    }

    private float GtoL(float qty){
        return qty / 1000;
    }

    private float GtoML(float qty){
        return qty;
    }

    private float GtoTBSP(float qty){
        return qty / 15;
    }

    private float GtoTSP(float qty){
        return qty / 5.7f;
    }

    private float GtoCUP(float qty){
        return qty / 340;
    }


}
