//package iec61850.nodes.protection.relatedFunction;
//
//import iec61850.nodes.common.LN;
//import iec61850.objects.measurements.WYE;
//import iec61850.objects.measurements.settings.ASG;
//import iec61850.objects.measurements.settings.ING;
//import iec61850.objects.measurements.settings.SPG;
//import iec61850.objects.protection.*;
//import iec61850.objects.samples.Attribute;
//import iec61850.objects.statusControl.status.SPS;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Getter @Setter
//public class RPSB1 extends LN {
//
//    private WYE Z = new WYE();
//    // Start (power swing detected) C1
//    private ACD tr = new ACD();
//    // Operate (out of step tripping) T C2
//    private ACT Op = new ACT();
//    //Blocking of PDIS zone C1
//    private SPS BlkZn = new SPS();
//    private Attribute<Boolean> blkzn1 = new Attribute(false);
//    //Settings
//    //Zero enable
//    private SPG ZeroEna = new SPG();
//    // Negative sequence current supervision enabled
//    private SPG NgEna = new SPG();
//    // Maximum current supervision enabled
//    private SPG MaxEna = new SPG();
//
//    // Power swing delta
//    private ASG SwgVal = new ASG();
//    // Power swing delta R
//    private ASG SwgRis = new ASG();
//    private Attribute<Float> SwgRis_a = new Attribute(0);
//    // Power swing delta X
//    private ASG SwgReact = new ASG();
//    private Attribute<Float> SwgReact_a = new Attribute(0);
//    // Power swing time
//
//    private ING SwgTmms = new ING();
//    // Unblocking time
//    private ING UnBlkTmms = new ING();
//    private int start_cal = 0;
//    private boolean start_flag = false;
//    private WYE Z_before = new WYE();
//    private boolean bloack_phA = false;
//    private boolean bloack_phB = false;
//    private boolean bloack_phC = false;
//
//    private int count_sv = 50;
//    private int count_cal = 0;
//    @Override
//    public void process() {
//        if(start_flag) {
//            if (count_cal < count_sv){
//                count_cal++;
//            }
//            else {
//                WYE Z_after = new WYE();
//                Z_after = Z;
//                block_R_X(SwgRis, SwgReact, Z_before, Z_after);
//                if ((SwgRis.getSetMagA().getF().getValue() < 200 && SwgReact.getSetMagA().getF().getValue() < 100) &&
//                        (SwgRis.getSetMagB().getF().getValue() < 200 && SwgReact.getSetMagB().getF().getValue() < 100) &&
//                        (SwgRis.getSetMagC().getF().getValue() < 200 && SwgReact.getSetMagC().getF().getValue() < 100))
//                {
//                    BlkZn.getStVal().setValue(false);
//                    blkzn1.setValue(true);
//                    Z_before.getPhsAB().getCVal().getMag().getF().setValue(Z_after.getPhsAB().getCVal().getMag().getF().getValue());
//                    Z_before.getPhsAB().getCVal().getAng().getF().setValue(Z_after.getPhsAB().getCVal().getAng().getF().getValue());
//                    Z_before.getPhsBC().getCVal().getMag().getF().setValue(Z_after.getPhsBC().getCVal().getMag().getF().getValue());
//                    Z_before.getPhsBC().getCVal().getAng().getF().setValue(Z_after.getPhsBC().getCVal().getAng().getF().getValue());
//                    Z_before.getPhsCA().getCVal().getMag().getF().setValue(Z_after.getPhsCA().getCVal().getMag().getF().getValue());
//                    Z_before.getPhsCA().getCVal().getAng().getF().setValue(Z_after.getPhsCA().getCVal().getAng().getF().getValue());
//                    count_cal = 0;
//                }
//                else {
//
//                    BlkZn.getStVal().setValue(false);
//                    blkzn1.setValue(false);
//
//                    Z_before.getPhsAB().getCVal().getMag().getF().setValue(Z_after.getPhsAB().getCVal().getMag().getF().getValue());
//                    Z_before.getPhsAB().getCVal().getAng().getF().setValue(Z_after.getPhsAB().getCVal().getAng().getF().getValue());
//                    Z_before.getPhsBC().getCVal().getMag().getF().setValue(Z_after.getPhsBC().getCVal().getMag().getF().getValue());
//                    Z_before.getPhsBC().getCVal().getAng().getF().setValue(Z_after.getPhsBC().getCVal().getAng().getF().getValue());
//                    Z_before.getPhsCA().getCVal().getMag().getF().setValue(Z_after.getPhsCA().getCVal().getMag().getF().getValue());
//                    Z_before.getPhsCA().getCVal().getAng().getF().setValue(Z_after.getPhsCA().getCVal().getAng().getF().getValue());
//                    count_cal = -(int)(80/0.02);
//                }
//            }
//        }
//        else {
//
//            start_cal++;
//            if(start_cal>160){
//                blkzn1.setValue(false);
//                Z_before.getPhsAB().getCVal().getMag().getF().setValue(Z.getPhsAB().getCVal().getMag().getF().getValue());
//                Z_before.getPhsAB().getCVal().getAng().getF().setValue(Z.getPhsAB().getCVal().getAng().getF().getValue());
//                Z_before.getPhsBC().getCVal().getMag().getF().setValue(Z.getPhsBC().getCVal().getMag().getF().getValue());
//                Z_before.getPhsBC().getCVal().getAng().getF().setValue(Z.getPhsBC().getCVal().getAng().getF().getValue());
//                Z_before.getPhsCA().getCVal().getMag().getF().setValue(Z.getPhsCA().getCVal().getMag().getF().getValue());
//                Z_before.getPhsCA().getCVal().getAng().getF().setValue(Z.getPhsCA().getCVal().getAng().getF().getValue());
//
//                start_flag = true;
//            }
//
//        }
//
//    }
//
//    public void block_R_X(ASG SwgRis, ASG SwgReact, WYE Z_before, WYE Z_after){
//
//
//        SwgRis.getSetMagA().getF().setValue(Math.abs((float)(Z_after.getPhsAB().getCVal().getMag().getF().getValue()
//                        *Math.cos(Math.PI/180.0*Z_after.getPhsAB().getCVal().getAng().getF().getValue())
//                        - Z_before.getPhsAB().getCVal().getMag().getF().getValue()
//                        *Math.cos(Math.PI/180.0*Z_before.getPhsAB().getCVal().getAng().getF().getValue())))
//        );
//        SwgRis.getSetMagB().getF().setValue(Math.abs((float)(Z_after.getPhsBC().getCVal().getMag().getF().getValue()
//                        *Math.cos(Math.PI/180.0*Z_after.getPhsBC().getCVal().getAng().getF().getValue())
//                        - Z_before.getPhsBC().getCVal().getMag().getF().getValue()
//                        *Math.cos(Math.PI/180.0*Z_before.getPhsBC().getCVal().getAng().getF().getValue())))
//        );
//        SwgRis.getSetMagC().getF().setValue(Math.abs((float)(Z_after.getPhsCA().getCVal().getMag().getF().getValue()
//                        *Math.cos(Math.PI/180.0*Z_after.getPhsCA().getCVal().getAng().getF().getValue())
//                        - Z_before.getPhsCA().getCVal().getMag().getF().getValue()
//                        *Math.cos(Math.PI/180.0*Z_before.getPhsCA().getCVal().getAng().getF().getValue())))
//        );
//        SwgReact.getSetMagA().getF().setValue(Math.abs((float)(Z_after.getPhsAB().getCVal().getMag().getF().getValue()
//                        *Math.sin(Math.PI/180.0*Z_after.getPhsAB().getCVal().getAng().getF().getValue())
//                        - Z_before.getPhsAB().getCVal().getMag().getF().getValue()
//                        *Math.sin(Math.PI/180.0*Z_before.getPhsAB().getCVal().getAng().getF().getValue())))
//        );
//        SwgReact.getSetMagB().getF().setValue(Math.abs((float)(Z_after.getPhsBC().getCVal().getMag().getF().getValue()
//                        *Math.sin(Math.PI/180.0*Z_after.getPhsBC().getCVal().getAng().getF().getValue())
//                        - Z_before.getPhsBC().getCVal().getMag().getF().getValue()
//                        *Math.sin(Math.PI/180.0*Z_before.getPhsBC().getCVal().getAng().getF().getValue())))
//        );
//        SwgReact.getSetMagC().getF().setValue(Math.abs((float)(Z_after.getPhsCA().getCVal().getMag().getF().getValue()
//                        *Math.sin(Math.PI/180.0*Z_after.getPhsCA().getCVal().getAng().getF().getValue())
//                        - Z_before.getPhsCA().getCVal().getMag().getF().getValue()
//                        *Math.sin(Math.PI/180.0*Z_before.getPhsCA().getCVal().getAng().getF().getValue())))
//        );
//
//        Z_before.getPhsAB().getCVal().getMag().getF().setValue(Z_after.getPhsAB().getCVal().getMag().getF().getValue());
//        Z_before.getPhsAB().getCVal().getAng().getF().setValue(Z_after.getPhsAB().getCVal().getAng().getF().getValue());
//        Z_before.getPhsBC().getCVal().getMag().getF().setValue(Z_after.getPhsBC().getCVal().getMag().getF().getValue());
//        Z_before.getPhsBC().getCVal().getAng().getF().setValue(Z_after.getPhsBC().getCVal().getAng().getF().getValue());
//        Z_before.getPhsCA().getCVal().getMag().getF().setValue(Z_after.getPhsCA().getCVal().getMag().getF().getValue());
//        Z_before.getPhsCA().getCVal().getAng().getF().setValue(Z_after.getPhsCA().getCVal().getAng().getF().getValue());
//    }
//
//}