DROP PROCEDURE IF EXISTS lowStockTransfer;
DELIMITER ;;

CREATE PROCEDURE lowStockTransfer()
BEGIN
  
  DECLARE part bigint;
  DECLARE lowstock int;
  DECLARE company int;
  DECLARE quantity int;
    DECLARE done INT DEFAULT FALSE;
  
  DECLARE cursor_i CURSOR FOR SELECT partid,lowstocklevel,reorderquantity, companyId  from masterParts where markForDelete = 0 AND lowstocklevel is not null;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
  
  		  OPEN cursor_i;
				  read_loop: LOOP
					FETCH cursor_i INTO part, lowstock, quantity, company;
					IF done THEN
					  LEAVE read_loop;
					END IF;
					
					INSERT INTO LowStockInventory (partid, lowstocklevel, reorderquantity, companyId, markForDelete) VALUES(part,lowstock,quantity, company, 0);
					 
				  END LOOP;
				  CLOSE cursor_i;
END;
;;