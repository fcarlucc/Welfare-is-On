import { Injectable } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { ApiService } from '../api/api.service';
@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(
    private sanitizer: DomSanitizer,
    private apiService: ApiService
  ) { }


  async fetchImage(imageId: string): Promise<SafeUrl> {
    let path: SafeUrl

    try {
      const imageBlob = await this.apiService.getImageAsPromise(imageId)
      // this.logBlob(imageBlob); // Log the blob details
      const objectURL = URL.createObjectURL(imageBlob)
      path = this.sanitizer.bypassSecurityTrustUrl(objectURL)
      // console.log('Sanitized Image URL:', path); // Log the sanitized URL
    } catch (error) {
      console.error('Image retrieval failed', error)
      throw error
    }
    return path
  }

  logBlob(blob: Blob): void {
    const reader = new FileReader()

    reader.onload = () => {
      console.log('Blob Data URL:', reader.result)
    };
    reader.readAsDataURL(blob)
  }
}