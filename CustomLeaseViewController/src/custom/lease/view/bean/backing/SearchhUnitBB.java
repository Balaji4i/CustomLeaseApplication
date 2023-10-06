package custom.lease.view.bean.backing;

import com.view.uiutils.ADFUtils;

import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SearchhUnitBB {
    public SearchhUnitBB() {
        super();
    }

    public void onClickStatusCount(ActionEvent actionEvent) { 
        String statusType = ADFContext.getCurrent()
                             .getPageFlowScope()
                             .get("statusType") == null ? "0" : ADFContext.getCurrent()
                                                                            .getPageFlowScope()
                                                                            .get("statusType")
                                                                            .toString(); 
        
        ViewObject dashVo = ADFUtils.findIterator("SearchPropertyUnitROVO1Iterator").getViewObject();
        dashVo.applyViewCriteria(null);
        dashVo.executeQuery();
        if(!"ALL".equals(statusType)){
            ViewCriteria vc = dashVo.createViewCriteria();
            ViewCriteriaRow vcRow = vc.createViewCriteriaRow(); 
            vcRow.setAttribute("Status", statusType);
            vc.addRow(vcRow);
            dashVo.applyViewCriteria(vc);
            dashVo.executeQuery(); 
        }    
    }

    public void exportToExcel(FacesContext facesContext,
                              OutputStream outputStream) {
        try {
        
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet =
                        workbook.createSheet("Unit Export");
                    HSSFRow rowhead = sheet.createRow((short)0);
                    rowhead.createCell(0).setCellValue("Unit No");
                    sheet.setColumnWidth(0, 5500);
                    rowhead.createCell(1).setCellValue("Unit Name");
                    sheet.setColumnWidth(1, 6500);
                    rowhead.createCell(2).setCellValue("Unit Shortcode");
                    sheet.setColumnWidth(2, 4500);
                    rowhead.createCell(3).setCellValue("Property Name");
                    sheet.setColumnWidth(3, 6500);
                    rowhead.createCell(4).setCellValue("Property Number");
                    sheet.setColumnWidth(4, 4500);
                    rowhead.createCell(5).setCellValue("Build Name");
                    sheet.setColumnWidth(5, 6500);
                    rowhead.createCell(6).setCellValue("Build No");
                    sheet.setColumnWidth(6, 4500);
                    rowhead.createCell(7).setCellValue("BU Name");
                    sheet.setColumnWidth(7, 6500);
                    rowhead.createCell(8).setCellValue("Floor No");
                    sheet.setColumnWidth(8, 4000);
                    rowhead.createCell(9).setCellValue("No of Rooms");
                    sheet.setColumnWidth(9, 4000);
                    rowhead.createCell(10).setCellValue("Unit Status");
                    sheet.setColumnWidth(10, 4500);
                    rowhead.createCell(11).setCellValue("Allot Type");
                    sheet.setColumnWidth(11, 6000);
                    rowhead.createCell(12).setCellValue("Unit Category");
                    sheet.setColumnWidth(12, 5500);
                    rowhead.createCell(13).setCellValue("Unit Type");
                    sheet.setColumnWidth(13, 6000);
                    rowhead.createCell(14).setCellValue("Area Type");
                    sheet.setColumnWidth(14, 6000);
                    rowhead.createCell(15).setCellValue("View Type");
                    sheet.setColumnWidth(15, 6000);
                   
                    
                    ViewObject actVO =
                        ADFUtils.findIterator("SearchPropertyUnitROVO1Iterator").getViewObject();
                    actVO.executeQuery();
            System.err.println("count++++"+actVO.getEstimatedRowCount());
                    if (actVO.getEstimatedRowCount() > 0) {
                        RowSetIterator rs = actVO.createRowSetIterator(null);
                        int excelRowData = 1;
                        while (rs.hasNext()) {
                            Row actsRow = rs.next();
 
                            String utNo =
                                actsRow.getAttribute("UnitNumber") != null ?
                                actsRow.getAttribute("UnitNumber").toString() :
                                "";
                            String utName =
                                actsRow.getAttribute("UnitName") != null ?
                                actsRow.getAttribute("UnitName").toString() :
                                "";
                            String utShrtCode =
                                actsRow.getAttribute("UnitShortcode") != null ?
                                actsRow.getAttribute("UnitShortcode").toString() :
                                "";
                            String propName =
                                actsRow.getAttribute("PropertyName") != null ?
                                actsRow.getAttribute("PropertyName").toString() : "";
                            String propNo =
                                actsRow.getAttribute("PropertyNumber") != null ? 
                                actsRow.getAttribute("PropertyNumber").toString() :
                                "";
                            String buildName =
                                actsRow.getAttribute("BuildName") != null ?
                                actsRow.getAttribute("BuildName").toString() :
                                "";
                            String buildNo =
                                actsRow.getAttribute("BuildNumber") != null ?
                                actsRow.getAttribute("PropertyName").toString() :
                                "";          
                            String orgName =
                                actsRow.getAttribute("OrgName") != null ?
                                actsRow.getAttribute("OrgName").toString() :
                                "";
                            String floorNo =
                                actsRow.getAttribute("FloorNumber") != null ?
                                actsRow.getAttribute("FloorNumber").toString() :
                                "";
                            String noOfRooms =
                                actsRow.getAttribute("NoOfRooms") != null ?
                                actsRow.getAttribute("NoOfRooms").toString() :
                                "";
                            String sts =
                                actsRow.getAttribute("Status") != null ?
                                actsRow.getAttribute("Status").toString() :
                                "";
                            String allotTyp =
                                actsRow.getAttribute("AllotType") != null ?
                                actsRow.getAttribute("AllotType").toString() :
                                "";
                            String utCatgDisp =
                                actsRow.getAttribute("UnitCategoryDisp") != null ?
                                actsRow.getAttribute("UnitCategoryDisp").toString() :
                                "";
                            String utTypDisp =
                                actsRow.getAttribute("UnitTypeDisp") != null ?
                                actsRow.getAttribute("UnitTypeDisp").toString() :
                                "";
                            String areaTypDisp =
                                actsRow.getAttribute("AreaTypeDisp") != null ?
                                actsRow.getAttribute("AreaTypeDisp").toString() :
                                "";
                            String vwTypDip =
                                actsRow.getAttribute("ViewTypeDisp") != null ?
                                actsRow.getAttribute("ViewTypeDisp").toString() :
                                "";                     
                            
                            HSSFRow row = sheet.createRow((short)excelRowData);
                            row.createCell(0).setCellValue(utNo);
                            row.createCell(1).setCellValue(utName);
                            row.createCell(2).setCellValue(utShrtCode);
                            row.createCell(3).setCellValue(propName);
                            row.createCell(4).setCellValue(propNo);
                            row.createCell(5).setCellValue(buildName);
                            row.createCell(6).setCellValue(buildNo);
                            row.createCell(7).setCellValue(orgName);
                            row.createCell(8).setCellValue(floorNo);
                            row.createCell(9).setCellValue(noOfRooms);
                            row.createCell(10).setCellValue(sts);
                            row.createCell(11).setCellValue(allotTyp);
                            row.createCell(12).setCellValue(utCatgDisp);
                            row.createCell(13).setCellValue(utTypDisp);
                            row.createCell(14).setCellValue(areaTypDisp);
                            row.createCell(15).setCellValue(vwTypDip);
                            
                            excelRowData++;
                        }
                    }
                    workbook.write(outputStream);
                    outputStream.flush();
                } catch (Exception e) {
                    System.err.println("BDS" + e.getMessage());
                }
    }
}
