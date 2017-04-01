import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

@Injectable()
export class TestEntityService {
  private testEntity: Object = {
    id: Number,
    name: String,
    description: String
  };

  constructor(http: Http) {
    this.testEntity = http.get("/api/test").subscribe()
  }
}
