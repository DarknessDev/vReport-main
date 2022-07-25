package br.vootytools.teamdev.reports.reflection.acessors;

import java.lang.reflect.Field;

import br.vootytools.teamdev.reports.reflection.Accessors;

public class FieldAccessor<TField> {
   private Field handle;

   public FieldAccessor(Field field) {
      this(field, false);
   }

   public FieldAccessor(Field field, boolean forceAccess) {
      this.handle = field;
      if (forceAccess) {
         Accessors.setAccessible(field);
      }

   }

   public TField get(Object target) {
      try {
         return (TField) this.handle.get(target);
      } catch (ReflectiveOperationException var3) {
         throw new RuntimeException("Cannot access field.", var3);
      }
   }

   public void set(Object target, TField value) {
      try {
         this.handle.set(target, value);
      } catch (ReflectiveOperationException var4) {
         throw new RuntimeException("Cannot access field.", var4);
      }
   }

   public boolean hasField(Object target) {
      return target != null && this.handle.getDeclaringClass().equals(target.getClass());
   }

   public Field getHandle() {
      return this.handle;
   }

   public String toString() {
      return "FieldAccessor[class=" + this.handle.getDeclaringClass().getName() + ", name=" + this.handle.getName() + ", type=" + this.handle.getType() + "]";
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj instanceof FieldAccessor) {
         FieldAccessor<?> other = (FieldAccessor)obj;
         return other.handle.equals(this.handle);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.handle.hashCode();
   }
}
