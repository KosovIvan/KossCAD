package mai.geomod.kosscad.moving;

import mai.geomod.kosscad.util.WorkSpace;

public interface IMovable<T> {
    public void Move(WorkSpace space, T obj);
}
