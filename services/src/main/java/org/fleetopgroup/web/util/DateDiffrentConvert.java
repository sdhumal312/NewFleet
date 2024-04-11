package org.fleetopgroup.web.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateDiffrentConvert {


	// convert image size code
	public String DifferentDate(Date Databasedate) throws Exception {
		try {
			// get Current days
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

			if (Databasedate != null) {

				int diffInDays = (int) ((Databasedate.getTime() - toDate.getTime()) / (1000 * 60 * 60 * 24));

				// System.out.println(diffInDays);
				String diffenceDays = null;

				switch (diffInDays) {
				case 0:
					long diffHours = (Databasedate.getTime() - toDate.getTime()) / (60 * 60 * 1000);

					if (diffHours == 0) {
						long diffMinutes = (Databasedate.getTime() - toDate.getTime()) / (60 * 1000);
						diffenceDays = (diffMinutes + " minutes ago");
					} else {
						diffenceDays = (diffHours + " hours ago");
					}
					break;
				case -1:
					diffenceDays = ("yesterDay");
					break;
				case 1:
					diffenceDays = ("tomorrow");
					break;
				default:
					if (diffInDays < -1) {
						long days = -diffInDays;
						if (days >= 365) {
							diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
						} else if (days > 30) {
							diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
						} else
							diffenceDays = (-diffInDays + " days ago");
					} else if (diffInDays > 1) {
						if (diffInDays >= 365) {
							diffenceDays = (diffInDays / 365 + " years & " + ((diffInDays % 365) / 31)
									+ " months  from now");
						} else if (diffInDays > 30) {
							diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
						} else
							diffenceDays = (diffInDays + " days from now");
					}
					break;
				}

				return diffenceDays;
			}
		} catch (Exception e) {
			throw new Exception("IOException in scale");
		}
		return null;
	}


}
