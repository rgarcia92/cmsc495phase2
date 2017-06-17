/*
 * Copyright (C) 2017 Rob Garcia at rgarcia92.student.umuc.edu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cmsc495phase1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob Garcia at rgarcia92.student.umuc.edu
 */
public class DataAccess {
     /**
     * Retrieve all medications
     * @return An array of Medication objects
     */
    public static ArrayList<Medications> selectMedicationsByGenericName() {
        ArrayList<Medications> allMedications = new ArrayList<>();
        String sql = "SELECT * FROM medications";
        try {
            Connection conn = Utilities.connectToDatabase("medications.db");
            PreparedStatement stmt = conn.prepareStatement(sql);
            /* Set values here */
            ResultSet rs = stmt.executeQuery();
            // Loop through the result set twice
            // Add by generic name in the first loop...
            while (rs.next()) {
                allMedications.add(new Medications(
                        rs.getInt("MEDID"),
                        rs.getString("GNAME") + " (G)",
                        rs.getString("BNAME") + " (B)",
                        rs.getString("COND1"),
                        rs.getString("COND2"),
                        rs.getString("COND3"),
                        rs.getInt("BTFLAG")
                ));
            }
            // Sorting
            allMedications.sort(Comparator.comparing(med -> med.gName));
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Return results */
        return allMedications;
    }
}