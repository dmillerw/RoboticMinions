package dmillerw.minion.core.helper.nbt;

import dmillerw.minion.core.helper.nbt.wrapper.FieldWrapper;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author dmillerw
 */
public class NBTHelper {

	public static boolean hasKey(NBTTagCompound nbt, String tag) {
		return hasKey(nbt, tag, -1);
	}

	public static boolean hasKey(NBTTagCompound nbt, String tag, int type) {
		if (tag == null || tag.isEmpty()) {
			throw new IllegalArgumentException("Cannot search for null or empty tag!");
		}

		if (type == -1) {
			return nbt.hasKey(tag);
		} else {
			return nbt.hasKey(tag, type);
		}
	}

	public static void writeFields(NBTTagCompound nbt, Object obj) {
		for (final Field field : obj.getClass().getDeclaredFields()) {
			if (!Modifier.isTransient(field.getModifiers()) && field.isAccessible()) {
				try {
					String name = field.getName();
					Class<?> type = field.getType();

					if (type.isPrimitive()) {
						String typeName = type.getName();

						// Grr, I want String switch statements
						// or Rust's match blocks
						if (typeName.contains("byte")) {
							nbt.setByte(name, field.getByte(obj));
						} else if (typeName.contains("boolean")) {
							nbt.setBoolean(name, field.getBoolean(obj));
						} else if (typeName.contains("double")) {
							nbt.setDouble(name, field.getDouble(obj));
						} else if (typeName.contains("float")) {
							nbt.setFloat(name, field.getFloat(obj));
						} else if (typeName.contains("int")) {
							nbt.setInteger(name, field.getInt(obj));
						} else if (typeName.contains("String")) {
							nbt.setString(name, (String) field.get(obj));
						}
					} else {
						FieldWrapper.get(type).writeToNBT(nbt, name, field.get(obj));
					}
				} catch (IllegalAccessException e) {
					// Spit out error
					continue;
				}
			}
		}
	}

}
