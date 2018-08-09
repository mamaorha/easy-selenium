package co.il.nmh.easy.selenium.data;

import java.awt.image.BufferedImage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Maor Hamami
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageVerificationResponse extends VerificationResponse
{
	private BufferedImage delta;
}
