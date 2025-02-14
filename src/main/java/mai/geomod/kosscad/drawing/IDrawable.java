package mai.geomod.kosscad.drawing;

import mai.geomod.kosscad.util.WorkSpace;

public interface IDrawable<T> {
    public void Draw(WorkSpace space, T obj);
}