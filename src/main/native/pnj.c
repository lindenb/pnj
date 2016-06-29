#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <png.h>

#include "pnj.h"

#define TO_LONG(a) ((jlong)(intptr_t)a)
#define FROM_LONG(t,a) ((t)((intptr_t)a)) 

#define QUALIFIEDMETHOD(fun) Java_com_github_lindenb_pnj_jni_##fun
#define PACKAGEPATH "com/github/lindenb/pnj/jni/"


#define WHERE fprintf(stderr,"[%s][%d]\n",__FILE__,__LINE__)

#define VERIFY_NOT_NULL(a) if((a)==0) do {fprintf(stderr,"Throwing error from %s %d\n",__FILE__,__LINE__); throwIOException(env,"Method returned Null");} while(0)

#define CAST_REF_OBJECT(retType,method,ref) \
static retType _get##method(JNIEnv * env, jobject self)\
	{\
	jlong ptr;jfieldID field;\
	jclass c= (*env)->GetObjectClass(env,(self));\
	VERIFY_NOT_NULL(c);\
	field = (*env)->GetFieldID(env,c, ref, "J");\
	VERIFY_NOT_NULL(field);\
	ptr= (*env)->GetLongField(env,self,field);\
	return (retType)ptr;\
	}\
static void _set##method(JNIEnv * env, jobject self,retType value)\
	{\
	jfieldID field;\
	jclass c= (*env)->GetObjectClass(env,(self));\
	VERIFY_NOT_NULL(c);\
	field = (*env)->GetFieldID(env,c, ref, "J");\
	VERIFY_NOT_NULL(field);\
	(*env)->SetLongField(env,self, field,(jlong)value);\
	}	

static void throwIOException(JNIEnv *env,const char* msg)
	{
	jclass newExcCls = (*env)->FindClass(env,"java/io/IOException");
	(*env)->ThrowNew(env, newExcCls, msg);
	}

/***************************************************************************************************/
/***************************************************************************************************/
/***************************************************************************************************/
/***************************************************************************************************/
//CAST_REF_OBJECT(bwaidx_t*,BwaIndex,"ref")



JNIEXPORT jlong JNICALL Java_com_github_lindenb_pnj_jni_PngWriterFactory__1png_1create_1write_1struct
  (JNIEnv* env, jclass c)
  {
  png_structp png = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
  return TO_LONG(png);
  }
 
 JNIEXPORT jlong JNICALL Java_com_github_lindenb_pnj_jni_PngWriterFactory__1png_1create_1info_1struct
  (JNIEnv *env, jclass c, jlong png)
  {
  png_infop info = png_create_info_struct(FROM_LONG(png_structp,png));
  return TO_LONG(info);
  }

JNIEXPORT jlong JNICALL Java_com_github_lindenb_pnj_jni_PngWriterFactory__1fopen
  (JNIEnv* env, jclass c, jstring filename)
  {
  long stream=0L;
  if(filename!=0)
  		{
	  	const char *str=(const char *) (*env)->GetStringUTFChars(env, filename, NULL);
		if (str == 0)
			{
			throwIOException(env,"GetStringUTFChars failed");
			}
		if(strcmp("-",str)==0)
			{
			stream = TO_LONG(stdout) ;
			}
		else
			{
			stream = TO_LONG(fopen(str,"wb"));
			}
		(*env)->ReleaseStringUTFChars(env, filename, str);
		}
   return stream;
  }


JNIEXPORT void JNICALL Java_com_github_lindenb_pnj_jni_PngWriterFactory__1png_1init_1io
  (JNIEnv *env, jclass c, jlong png, jlong fp) {
   png_init_io(FROM_LONG(png_structp,png), FROM_LONG(FILE*,fp));
  }

/***************************************************************************************************/
/***************************************************************************************************/
/***************************************************************************************************/
/***************************************************************************************************/

JNIEXPORT void JNICALL Java_com_github_lindenb_pnj_jni_PngWriterImpl__1png_1write_1end
  (JNIEnv* env, jclass c, jlong png) {
  png_write_end(FROM_LONG(png_structp,png), NULL);
  } 
 
JNIEXPORT void JNICALL Java_com_github_lindenb_pnj_jni_PngWriterImpl__1fclose
  (JNIEnv* env, jclass c, jlong fp) {
  fclose(FROM_LONG(FILE*,fp));
  } 
  
JNIEXPORT void JNICALL Java_com_github_lindenb_pnj_jni_PngWriterImpl__1png_1write_1row
  (JNIEnv *env, jclass c, jlong png, jbyteArray array)
  {
  jbyte* buf =  (*env)->GetByteArrayElements(env, array, 0);
  png_write_row(FROM_LONG(png_structp,png),(png_bytep)buf);
  (*env)->ReleaseByteArrayElements(env, array, buf, JNI_COMMIT);
  }
