package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class XepLoaiTest {

    // Hàm cần kiểm thử (giả lập lấy từ source code của Dev)
    public static String xepLoai(int diemTB, boolean coThiLai) {
        if (diemTB < 0 || diemTB > 10) { 
            return "Diem khong hop le";
        }
        if (diemTB >= 8.5) { 
            return "Gioi";
        } else if (diemTB >= 7.0) { 
            return "Kha";
        } else if (diemTB >= 5.5) { 
            return "Trung Binh";
        } else {
            if (coThiLai) { 
                return "Thi lai";
            }
            return "Yeu - Hoc lai";
        }
    }

    // --- CÁC TEST CASE (100% Branch Coverage) ---

    @Test(description = "TC1: Phủ nhánh N1-True")
    public void testDiemKhongHopLe() {
        Assert.assertEquals(xepLoai(-1, false), "Diem khong hop le");
        Assert.assertEquals(xepLoai(11, false), "Diem khong hop le"); // Test thêm biên trên
    }

    @Test(description = "TC2: Phủ nhánh N1-False, N2-True")
    public void testLoaiGioi() {
        Assert.assertEquals(xepLoai(9, false), "Gioi");
    }

    @Test(description = "TC3: Phủ nhánh N2-False, N3-True")
    public void testLoaiKha() {
        Assert.assertEquals(xepLoai(8, false), "Kha");
    }

    @Test(description = "TC4: Phủ nhánh N3-False, N4-True")
    public void testLoaiTrungBinh() {
        Assert.assertEquals(xepLoai(6, false), "Trung Binh");
    }

    @Test(description = "TC5: Phủ nhánh N4-False, N5-True")
    public void testThiLai() {
        Assert.assertEquals(xepLoai(4, true), "Thi lai");
    }

    @Test(description = "TC6: Phủ nhánh N5-False")
    public void testYeuHocLai() {
        Assert.assertEquals(xepLoai(4, false), "Yeu - Hoc lai");
    }
}