package com.twotoasters.android.horizontalimagescroller.io;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.twotoasters.android.horizontalimagescroller.image.ImageToLoadUrl;

public class ImageUrlRequest {
	final private ImageToLoadUrl _imageToLoadUrl;
	final private int _reqWidth;
	final private int _reqHeight;
	private ImageUrlRequestCacheKey _cacheKey;
	private String _cacheFileName;
	private int _imageFailedToLoadResourceId;
	
	public ImageUrlRequest(ImageToLoadUrl imageToLoadUrl, int reqWidth, int reqHeight) {
		_imageToLoadUrl = imageToLoadUrl;
		_reqWidth = reqWidth;
		_reqHeight = reqHeight;
	}
	
	public ImageToLoadUrl getImageToLoadUrl() {
		return _imageToLoadUrl;
	}
	
	public int getReqWidth() {
		return _reqWidth;
	}
	
	public int getReqHeight() {
		return _reqHeight;
	}
	
	public ImageUrlRequestCacheKey getCacheKey() {
		if (_cacheKey == null) {
			_cacheKey = new ImageUrlRequestCacheKey(_imageToLoadUrl.getUrl(), _imageToLoadUrl.getUsername(), _imageToLoadUrl.getPassword(), _reqWidth, _reqHeight);
		}
		return _cacheKey;
	}
	
	public String getCacheFileName() {
		if (_cacheFileName == null) {
			try {
				MessageDigest hash = MessageDigest.getInstance("SHA-256");
				String url = _imageToLoadUrl.getUrl();
				String username = _imageToLoadUrl.getUsername();
				String password = _imageToLoadUrl.getPassword();
				String toHash = String.format("url_%1$s_creds_%2$s%3$s_size_%4$dx%5$d", url, username, password, _reqWidth, _reqHeight);
				hash.update(toHash.getBytes());
				_cacheFileName = new String(Hex.encodeHex(hash.digest()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return _cacheFileName;
	}

	public int getImageFailedToLoadResourceId() {
		return _imageFailedToLoadResourceId;
	}

	public void setImageFailedToLoadResourceId(int imageFailedToLoadResourceId) {
		_imageFailedToLoadResourceId = imageFailedToLoadResourceId;
	}
}
