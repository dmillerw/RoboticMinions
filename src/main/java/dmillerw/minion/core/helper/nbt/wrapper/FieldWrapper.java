package dmillerw.minion.core.helper.nbt.wrapper;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dmillerw
 */
public abstract class FieldWrapper {

	public static Map<Class, FieldWrapper> classToWrapperMapping = new HashMap<Class, FieldWrapper>();

	static {

	}

	public static FieldWrapper get(Class clazz) {
		return classToWrapperMapping.get(clazz);
	}

	public abstract void writeToNBT(NBTTagCompound nbt, String name, Object value);

	public abstract Object readFromNBT(NBTTagCompound nbt, String name);

}
