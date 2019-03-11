package com.yc.phonerecycle.activity

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.yc.library.widget.BottomDialog
import com.yc.phonerecycle.mvp.view.BaseActivity
import com.yc.phonerecycle.R
import com.yc.phonerecycle.model.bean.base.BaseRep
import com.yc.phonerecycle.model.bean.biz.UploadFileRep
import com.yc.phonerecycle.model.bean.biz.UserInfoRep
import com.yc.phonerecycle.mvp.presenter.biz.CommonPresenter
import com.yc.phonerecycle.mvp.view.viewinf.CommonBaseIV
import com.yc.phonerecycle.utils.*
import com.yc.phonerecycle.widget.ListDialog
import kotlinx.android.synthetic.main.activity_edit_userinfo.*
import java.io.File
import java.util.*


class UserInfoActivity : BaseActivity<CommonPresenter>(), CommonBaseIV.UserInfoIV,CommonBaseIV.CommonIV,CommonBaseIV.UploadFileIV{
    override fun uploadFileSuccess(data: BaseRep?) {
        if (data?.code == 0) {
            ToastUtil.showShortToast("头像上传成功")
            presenter.getInfo()
        }
    }

    override fun getDataOK(rep: Any?) {
        if (rep is UploadFileRep) {
            if (rep.code == 0) {
                if (TextUtils.isEmpty(rep.data)) {
                    ToastUtil.showShortToast("上传头像失败")
                    return
                }
                presenter.changeLog(rep.data,UserInfoUtils.getUser().data?.userInfoVO?.id)
            }
        }
    }

    override fun createPresenter() = CommonPresenter()


    override fun initBundle() {
    }

    override fun getContentView(): Int = R.layout.activity_edit_userinfo

    private lateinit var mUploadfilePath: String

    val IMAGE_DIR = "phonerecycle/image"
    var mUploadfileName = "avatar.jpg"
    private var listDialog: ListDialog? = null
    private var cameraDialog: Dialog? = null
    internal val CROP_ALBUM = 1
    internal val CROP = 2
    internal val CROP_PICTURE = 3

    private var dialog : BottomDialog? = null

    override fun initView() {
        if (DeviceUtil.checkSDCardAvailable()) {
            mUploadfilePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + IMAGE_DIR
            FileUtils.makeDirs(mUploadfilePath, true)
        } else {
            mUploadfilePath = ""
            mUploadfileName = ""
        }
    }

    override fun initDatas() {
        presenter.getInfo()
        user_id.text = "ID:"+UserInfoUtils.getUser().data?.userInfoVO?.id
        avatar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                handleWithIconClick()
            }
        })
        userinfo_nick.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,String>()
                map["title"] = userinfo_nick.getTitle()
                map["type"] = "0"
                ActivityToActivity.toActivity(
                    this@UserInfoActivity, EditUserInfoActivity::class.java,map)
            }
        })
        userinfo_sign.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var map = HashMap<String,String>()
                map["title"] = userinfo_sign.getTitle()
                map["type"] = "1"
                ActivityToActivity.toActivity(
                    this@UserInfoActivity, EditUserInfoActivity::class.java,map)
            }
        })
    }

    override fun onResume() {
       super.onResume()
//        presenter.getInfo()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.getInfo()
    }

    override fun userInfoSuccess(body: UserInfoRep?) {
        UserInfoUtils.saveUserInfo(body)
        userinfo_nick.setSubTitle(UserInfoUtils.getUserInfo().data?.name)
        userinfo_sign.setSubTitle(UserInfoUtils.getUserInfo().data?.signature)
//        Glide.with(this@UserInfoActivity).load(body?.data?.logo).centerCrop().into(avatar)
        Glide.with(this@UserInfoActivity).load(body?.data?.logo).apply(RequestOptions.bitmapTransform(CircleCrop()).placeholder(R.drawable.logo)).into(avatar)
    }

    fun handleWithIconClick() {
        if (listDialog == null) {
            val itmes = arrayOf<String>(
                getString(R.string.photo),
                getString(R.string.choose_from_album),
                getString(R.string.cancel)
            )
            listDialog = ListDialog(this@UserInfoActivity, Arrays.asList(*itmes), "",
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    if (TextUtils.isEmpty(mUploadfilePath)) {
                        ToastUtil.showShortToast("SD卡不能使用，不能修改头像哦..")
                        return@OnItemClickListener
                    }
                    when (position) {
                        0 -> jumpToCamera()
                        1 -> jumpToAlbum()
                    }
                    if (listDialog != null) listDialog!!.dismiss()
                })
            val window = listDialog!!.getWindow()
            val lp = WindowManager.LayoutParams()
            lp.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            window.setAttributes(lp)
            listDialog!!.setCanceledOnTouchOutside(true)
        }
        if (!listDialog!!.isShowing()) listDialog!!.show()
    }

    /**
     * 跳转相机
     */
    internal fun jumpToCamera() {
        PermissionUtils.checkCameraPermission(this, object : PermissionUtils.Callback() {
            override fun onGranted() {
                //                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //                // 删除上一次截图的临时文件
                //                FileUtils.deleteFileInDir(mUploadfilePath, mUploadfileName, true);
                //                mUploadfileName = buildFileName();
                //                // 保存本次截图临时文件名字
                //                Uri imageUri = Uri.fromFile(new File(mUploadfilePath, mUploadfileName));
                //                // 指定照片保存路径（SD卡）
                //                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //                startActivityForResult(openCameraIntent, CROP);


                FileUtils.deleteFileInDir(mUploadfilePath, mUploadfileName, true)

                val cameraPhoto = File(mUploadfilePath, mUploadfileName)
                val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val photoUri = FileProvider.getUriForFile(this@UserInfoActivity, "$packageName.fileprovider", cameraPhoto)
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePhotoIntent, CROP)
            }

            override fun onRationale() {
                ToastUtil.showShortToast("请开启相机权限才能正常使用")
            }

            override fun onDenied(context: Context) {
                showPermissionDialog()
            }
        })
    }

    internal fun showPermissionDialog() {
        if (cameraDialog == null) {
            cameraDialog = DialogHelper.showDialog(
                "1",
                this@UserInfoActivity,
                null,
                "",
                "",
                getString(R.string.open_camera_rights),
                "你还没有开启相机权限，开启之后即可使用相机拍照",
                getString(R.string.cancel),
                getString(R.string.setting),
                "",
                "",
                object : BaseDialog.IClickListener {
                    override fun click(dialog: Dialog) {
                    }
                },
                object : BaseDialog.IClickListener {
                    override fun click(dialog: Dialog) {
                        //                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        //                            UserActivity.this.startActivity(intent);
                        PermissionUtils.openPermissionSettings(this@UserInfoActivity)
                    }
                }
            )
            cameraDialog?.setCanceledOnTouchOutside(false)
            cameraDialog?.setOnCancelListener(DialogInterface.OnCancelListener {
            })
        } else if (!cameraDialog?.isShowing()!!) {
            cameraDialog?.show()
        }
    }

    /**
     * 跳转相册
     */
    internal fun jumpToAlbum() {
        // 删除上一次截图的临时文件
        FileUtils.deleteFileInDir(mUploadfilePath, mUploadfileName, true)
        val openAlbumIntent: Intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            openAlbumIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            openAlbumIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        } else {
            openAlbumIntent = Intent(Intent.ACTION_GET_CONTENT)
        }
        openAlbumIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        openAlbumIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        openAlbumIntent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )

        if (JMAppUtil.isActivityExist(openAlbumIntent)) {
            startActivityForResult(openAlbumIntent, CROP_ALBUM)
        } else {
            ToastUtil.showShortToast(getString(R.string.unable_to_find_system_album))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CROP -> {
                    //                    Uri uri = Uri.fromFile(new File(mUploadfilePath, mUploadfileName));
                    val photoUri = FileProvider.getUriForFile(this@UserInfoActivity,
                        "$packageName.fileprovider", File(mUploadfilePath, mUploadfileName))
                    cropImage(photoUri, 1024, 1024, CROP_PICTURE)
                }
                CROP_ALBUM -> if (data != null && data.data != null) {
//                    mUploadfileName = buildFileName()
                    val uri1 = data.data
                    cropImage(uri1!!, 1024, 1024, CROP_PICTURE)
                }
                CROP_PICTURE -> {
                    val photo = File(mUploadfilePath, mUploadfileName)
                    if (!TextUtils.isEmpty(photo.absolutePath))
                        presenter.uploadFile(photo)
                }
            }
        }
    }

    /**
     * 图像截取
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    internal fun cropImage(uri: Uri, outputX: Int, outputY: Int, requestCode: Int) {
        //酷派手机系统定制，剪切有问题，采用自定义控件的方式剪切
        if (DeviceUtil.isCoolpad || DeviceUtil.isHuawei || DeviceUtil.isNexus || DeviceUtil.isMeitu || DeviceUtil.isZTE || DeviceUtil.isViVo || DeviceUtil.isSony || DeviceUtil.isXiaomi) {
            val i = Intent(this@UserInfoActivity, CropPictureActivity::class.java)
            i.putExtra(CropPictureActivity.PIC_URI, uri.toString())
            i.putExtra(CropPictureActivity.UPLOAD_FILE_PATH, mUploadfilePath)
            i.putExtra(CropPictureActivity.UPLOAD_FILE_NAME, mUploadfileName)
            startActivityForResult(i, CROP_PICTURE)
        } else {
            cropImageByDefault(uri, outputX, outputY, requestCode)
        }
    }

    // 拍照截取图片
    internal fun cropImageByDefault(uri: Uri, outputX: Int, outputY: Int, requestCode: Int) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", outputX)
        intent.putExtra("outputY", outputY)
        intent.putExtra("outputFormat", "JPEG")
        intent.putExtra("noFaceDetection", true)
        intent.putExtra("return-data", false)
        intent.putExtra("scaleUpIfNeeded", true)
        intent.putExtra("scale", true)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(mUploadfilePath, mUploadfileName)))
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        startActivityForResult(intent, requestCode)
    }

}

