namespace BluffinPokerClient
{
    partial class SplashCareerConnect
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SplashCareerConnect));
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.label6 = new System.Windows.Forms.Label();
            this.spbStep3 = new EricUtility.Windows.Forms.StatePictureBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.btnCancel = new System.Windows.Forms.Button();
            this.spbStep2 = new EricUtility.Windows.Forms.StatePictureBox();
            this.spbStep1 = new EricUtility.Windows.Forms.StatePictureBox();
            this.label1 = new System.Windows.Forms.Label();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.spbStep3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.spbStep2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.spbStep1)).BeginInit();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.White;
            this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.panel1.Controls.Add(this.panel2);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(284, 232);
            this.panel1.TabIndex = 0;
            // 
            // panel2
            // 
            this.panel2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.spbStep3);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.btnCancel);
            this.panel2.Controls.Add(this.spbStep2);
            this.panel2.Controls.Add(this.spbStep1);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(280, 228);
            this.panel2.TabIndex = 0;
            // 
            // label6
            // 
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(46, 156);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(208, 23);
            this.label6.TabIndex = 11;
            this.label6.Text = "Authenticating Player ...";
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // spbStep3
            // 
            this.spbStep3.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.spbStep3.Etat = EricUtility.Windows.Forms.StatePictureBoxStates.None;
            this.spbStep3.Location = new System.Drawing.Point(20, 156);
            this.spbStep3.Name = "spbStep3";
            this.spbStep3.Size = new System.Drawing.Size(20, 23);
            this.spbStep3.TabIndex = 10;
            this.spbStep3.TabStop = false;
            // 
            // label3
            // 
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(46, 117);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(208, 23);
            this.label3.TabIndex = 5;
            this.label3.Text = "Existence of Username ...";
            this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(46, 79);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(208, 23);
            this.label2.TabIndex = 4;
            this.label2.Text = "Reaching the server ...";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // btnCancel
            // 
            this.btnCancel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.btnCancel.Enabled = false;
            this.btnCancel.Location = new System.Drawing.Point(0, 203);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(278, 23);
            this.btnCancel.TabIndex = 3;
            this.btnCancel.Text = "Close";
            this.btnCancel.UseVisualStyleBackColor = true;
            this.btnCancel.Click += new System.EventHandler(this.btnCancel_Click);
            // 
            // spbStep2
            // 
            this.spbStep2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.spbStep2.Etat = EricUtility.Windows.Forms.StatePictureBoxStates.None;
            this.spbStep2.Location = new System.Drawing.Point(20, 117);
            this.spbStep2.Name = "spbStep2";
            this.spbStep2.Size = new System.Drawing.Size(20, 23);
            this.spbStep2.TabIndex = 2;
            this.spbStep2.TabStop = false;
            // 
            // spbStep1
            // 
            this.spbStep1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("spbStep1.BackgroundImage")));
            this.spbStep1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.spbStep1.Etat = EricUtility.Windows.Forms.StatePictureBoxStates.Waiting;
            this.spbStep1.Location = new System.Drawing.Point(20, 79);
            this.spbStep1.Name = "spbStep1";
            this.spbStep1.Size = new System.Drawing.Size(20, 23);
            this.spbStep1.TabIndex = 1;
            this.spbStep1.TabStop = false;
            // 
            // label1
            // 
            this.label1.Dock = System.Windows.Forms.DockStyle.Top;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold);
            this.label1.Location = new System.Drawing.Point(0, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(278, 50);
            this.label1.TabIndex = 0;
            this.label1.Text = "Start Playing ...";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // SplashCareerConnect
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 232);
            this.Controls.Add(this.panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "SplashCareerConnect";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "TrainingConnectForm";
            this.panel1.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.spbStep3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.spbStep2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.spbStep1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label label1;
        private EricUtility.Windows.Forms.StatePictureBox spbStep2;
        private EricUtility.Windows.Forms.StatePictureBox spbStep1;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label6;
        private EricUtility.Windows.Forms.StatePictureBox spbStep3;
    }
}