
export class Recipe {
	public name : string;
	public description : string;
	public imagePath : string;
	
	constructor( name:string, description : string, image_path :string ) {
		this.name = name;
		this.description = description;
		this.imagePath = image_path;
	}
}